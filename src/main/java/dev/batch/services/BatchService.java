package dev.batch.services;

import dev.batch.dto.*;
import dev.batch.models.Batch;
import dev.batch.models.BatchAssociates;
import dev.batch.repositories.BatchAssociatesRepository;
import dev.batch.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.util.*;

@Service
public class BatchService {

	@Autowired
	private BatchRepository batchRepository;
	@Autowired
	private BatchAssociatesRepository batchAssociatesRepository;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CurriculumService curriculumService;


    public BatchResponse getBatchInfoAndAveragesById(long id) {
        // Get name, description and instructor of batch -- will later be passed to BatchResponse object


        List<EmployeeDTO> employeeDTOS = getAllAssociates(id, true, true, true);


        List<EmployeeQuizScore> empQuiz = getQuizScoresFromEmployees(employeeDTOS);
        List<EmployeeTopicCompetency> employeeTopicCompetencies = getTopicCompetenciesFromEmployees(employeeDTOS);
        List<EmployeeQCFeedback> employeeQCFeedbacks = getQCFeedBackFromEmployees(employeeDTOS);


        Batch batch = getBasicBatchInfo(id);

        //quizAverages for batch
        System.out.println(empQuiz);
        Map<Long, List<String>> quizAverages = getQuizAveragesInfo(empQuiz);

		//topic competency averages for batch
        Map<Long, List<String>> techAverages = getTopicCompetencyAveragesInfo(employeeTopicCompetencies);

        // personal qc rating averages
        Map<Long, List<String>> qcRatingAverages = new TreeMap<>();

        // instructor feedback averages
        Map<Long, List<String>> qcInstructorFeedbackAverages = getQCInstructorFeedbackAverages(employeeQCFeedbacks);


        return new BatchResponse(batch, quizAverages, techAverages, qcRatingAverages, qcInstructorFeedbackAverages);
    }



    private Batch getBasicBatchInfo(Long id) {
        return batchRepository.findById(id).orElse(null);

    }

    public List<EmployeeQuizScore> getQuizScoresFromEmployees(List<EmployeeDTO> employeeDTOS) {
        List<EmployeeQuizScore> empQuiz = new LinkedList<>();
        for (int i = 0; i < employeeDTOS.size(); i++) {
            if (employeeDTOS.get(i).getQuizScores() != null) {
                empQuiz.addAll(employeeDTOS.get(i).getQuizScores());
            }

        }
        return empQuiz;
    }

    private List<QuizDTO> grabQuizNames(List<EmployeeQuizScore> empQuiz) {
        // Grab quiz ids from EmployeeQuiz objects returned from the Employee Service
        List<Long> quizIds = new ArrayList<>();
        for (int i = 0; i < empQuiz.size(); i++) {
            if (!(quizIds.contains(empQuiz.get(i).getId().getQuizId()))) {
                quizIds.add(empQuiz.get(i).getId().getQuizId());
            }

        }
        System.out.println(quizIds);
        // Send a list of these quiz ids to the Curriculum Service to get associated quiz name
       return curriculumService.getQuizzesByListOfIds(quizIds);
    }

    private Map<Long, List<String>> getQuizAveragesInfo(List<EmployeeQuizScore> empQuiz) {
        if (empQuiz.size() == 0) {
            return new TreeMap<>();
        }
        // Retrive quiz names from curriculum service
        List<QuizDTO> quizDTOS = grabQuizNames(empQuiz);
        System.out.println(quizDTOS);
        System.out.println(quizDTOS.get(0).getId());

        // Calculate quiz averages and format quiz average output
        Map<Long, List<String>> averageScoreForQuizzes = new TreeMap<>();
        Map<Long, List<Float>> scoresForQuiz = new TreeMap<>();
        for (int i = 0; i < empQuiz.size(); i++) {
            Long quizId = empQuiz.get(i).getId().getQuizId();
            if (!(scoresForQuiz.containsKey(quizId))){
                scoresForQuiz.put(quizId, new ArrayList<>());
                scoresForQuiz.get(quizId).add(empQuiz.get(i).getScore());
                averageScoreForQuizzes.put(quizId, new ArrayList<>());
                for (int j = 0; j < quizDTOS.size(); j++) {
                    System.out.println(quizId);
                    System.out.println(quizDTOS.get(j).getId());
                    if (quizId.equals(quizDTOS.get(j).getId())) {
                        String quizName = quizDTOS.get(j).getName();
                        System.out.println(quizName);
                        averageScoreForQuizzes.get(quizId).add(quizName);
                        break;
                    }
                }
            }
            else {
                scoresForQuiz.get(quizId).add(empQuiz.get(i).getScore());
            }
        }

        for (Long key : scoresForQuiz.keySet()) {
            int length = scoresForQuiz.get(key).size();
            float sum = 0;
            for (int i = 0; i < length; i++) {
                sum += scoresForQuiz.get(key).get(i);
            }
            float average = (float) sum/length;
            String formattedAverage = String.format("%.2f", average);
            String formatCount = Integer.toString(length);
            averageScoreForQuizzes.get(key).add(formattedAverage);
            averageScoreForQuizzes.get(key).add(formatCount);

        }

        return averageScoreForQuizzes;
    }

    public List<EmployeeTopicCompetency> getTopicCompetenciesFromEmployees(List<EmployeeDTO> employeeDTOS) {
        List<EmployeeTopicCompetency> employeeTopicCompetencies = new ArrayList<>();
        for (int i = 0; i < employeeDTOS.size(); i++) {
            if (employeeDTOS.get(i).getTopicCompetencies() != null) {
                employeeTopicCompetencies.addAll(employeeDTOS.get(i).getTopicCompetencies());
            }
        }

        return employeeTopicCompetencies;
    }

    private List<TopicDTO> grabTopicsAndTechNames(List<EmployeeTopicCompetency> employeeTopicCompetencies) {

        List<Long> topicIds = new ArrayList<>();
        for (int i = 0; i < employeeTopicCompetencies.size(); i++) {
            if (!(topicIds.contains(employeeTopicCompetencies.get(i).getId().getTopicId()))) {
                topicIds.add(employeeTopicCompetencies.get(i).getId().getTopicId());
            }
        }

        return curriculumService.getTopicsByListOfIds(topicIds);
    }


    private Map<Long, List<String>> getTopicCompetencyAveragesInfo(List<EmployeeTopicCompetency> employeeTopicCompetencies) {

        if (employeeTopicCompetencies.size() == 0) {
            return new TreeMap<>();
        }
       List<TopicDTO> topicDTOS = grabTopicsAndTechNames(employeeTopicCompetencies);

        Map<Long, List<String>> averageTopicCompetencies = new TreeMap<>();
        Map<Long, List<Float>> competenciesForTechnology = new TreeMap<>();
        for (int i = 0; i < employeeTopicCompetencies.size(); i++) {
            Long topicId = employeeTopicCompetencies.get(i).getId().getTopicId();
            if (!(competenciesForTechnology.containsKey(topicId))){
                competenciesForTechnology.put(topicId, new ArrayList<>());
                competenciesForTechnology.get(topicId).add(employeeTopicCompetencies.get(i).getCompetency());
                averageTopicCompetencies.put(topicId, new ArrayList<>());
                for (int j = 0; j < topicDTOS.size(); j++) {
                    if (topicId.equals(topicDTOS.get(j).getTopicId())) {
                        String techName = topicDTOS.get(j).getTechName();
                        averageTopicCompetencies.get(topicId).add(techName);
                        break;
                    }
                }
            }
            else {
                competenciesForTechnology.get(topicId).add(employeeTopicCompetencies.get(i).getCompetency());
            }
        }

        for (Long key : competenciesForTechnology.keySet()) {
            int length = competenciesForTechnology.get(key).size();
            float sum = 0;
            for (int i = 0; i < length; i++) {
                sum += competenciesForTechnology.get(key).get(i);
            }
            float average = (float) sum/length;
            String formattedAverage = String.format("%.1f", average);
            String formatCount = Integer.toString(length);
            averageTopicCompetencies.get(key).add(formattedAverage);
            averageTopicCompetencies.get(key).add(formatCount);

        }

        return averageTopicCompetencies;
    }

    public List<EmployeeQCFeedback> getQCFeedBackFromEmployees(List<EmployeeDTO> employeeDTOS) {
        List<EmployeeQCFeedback> empQCFeedbacks = new ArrayList<>();
        for (int i = 0; i < employeeDTOS.size(); i++) {
            if (employeeDTOS.get(i).getQcFeedbacks() != null) {
                empQCFeedbacks.addAll(employeeDTOS.get(i).getQcFeedbacks());
            }

        }
        return empQCFeedbacks;

    }

    public List<QCDTO> grabQCNames(List<EmployeeQCFeedback> employeeQCFeedbacks) {
        List<Long> qcIds = new ArrayList<>();
        for (int i = 0; i < employeeQCFeedbacks.size(); i++) {
            if (!(qcIds.contains(employeeQCFeedbacks.get(i).getId().getQcId()))){
                qcIds.add(employeeQCFeedbacks.get(i).getId().getQcId());
            }

        }
        // Send a list of these qc ids to the Curriculum Service to get associated qc name
        return curriculumService.getQCNamesByListOfIds(qcIds);
    }

    public Map<Long, List<String>> getQCRatingsAverages(List<EmployeeQCFeedback> employeeQCFeedbacks) {
        if (employeeQCFeedbacks.size() == 0) {
            return new TreeMap<>();
        }
        List<QCDTO> qcDTOs = grabQCNames(employeeQCFeedbacks);

        Map<Long, List<String>> averageQCRatings = new TreeMap<>();
        Map<Long, List<Integer>> ratingsForQC = new TreeMap<>();
        for (int i = 0; i < employeeQCFeedbacks.size(); i++) {
            Long qcId = employeeQCFeedbacks.get(i).getId().getQcId();
            if (!(ratingsForQC.containsKey(qcId))){
                ratingsForQC.put(qcId, new ArrayList<>());
                ratingsForQC.get(qcId).add(employeeQCFeedbacks.get(i).getAssociateRating());
                averageQCRatings.put(qcId, new ArrayList<>());
                for (int j = 0; j < qcDTOs.size(); j++) {
                    if (qcId.equals(qcDTOs.get(j).getId())) {
                        String qcName = qcDTOs.get(j).getName();
                        averageQCRatings.get(qcId).add(qcName);
                        break;
                    }
                }
            }
            else {
                ratingsForQC.get(qcId).add(employeeQCFeedbacks.get(i).getAssociateRating());
            }
        }

        for (Long key : ratingsForQC.keySet()) {
            int length = ratingsForQC.get(key).size();
            int sum = 0;
            /*for (int i = 0; i < length; i++) {
                sum += ratingsForQC.get(key).get(i);
            }*/
            float average = (float) sum/length;
            String formattedAverage = String.format("%.1f", average);
            String formatCount = Integer.toString(length);
            averageQCRatings.get(key).add(formattedAverage);
            averageQCRatings.get(key).add(formatCount);

        }

        return averageQCRatings;
    }

    public Map<Long, List<String>> getQCInstructorFeedbackAverages(List<EmployeeQCFeedback> employeeQCFeedbacks) {
        if (employeeQCFeedbacks.size() == 0) {
            return new TreeMap<>();
        }
        List<QCDTO> qcDTOs = grabQCNames(employeeQCFeedbacks);

        Map<Long, List<String>> averageQCInstructorFeedbacks = new TreeMap<>();
        Map<Long, List<Integer>> feedbacksForQC = new TreeMap<>();
        for (int i = 0; i < employeeQCFeedbacks.size(); i++) {
            Long qcId = employeeQCFeedbacks.get(i).getId().getQcId();
            if (!(feedbacksForQC.containsKey(qcId))){
                feedbacksForQC.put(qcId, new ArrayList<>());
                feedbacksForQC.get(qcId).add(employeeQCFeedbacks.get(i).getInstructorFeedback());
                averageQCInstructorFeedbacks.put(qcId, new ArrayList<>());
                for (int j = 0; j < qcDTOs.size(); j++) {
                    if (qcId.equals(qcDTOs.get(j).getId())) {
                        String qcName = qcDTOs.get(j).getName();
                        averageQCInstructorFeedbacks.get(qcId).add(qcName);
                        break;
                    }
                }
            }
            else {
                feedbacksForQC.get(qcId).add(employeeQCFeedbacks.get(i).getInstructorFeedback());
            }
        }

        for (Long key : feedbacksForQC.keySet()) {
            int length = feedbacksForQC.get(key).size();
            int sum = 0;
            for (int i = 0; i < length; i++) {
                sum += feedbacksForQC.get(key).get(i);
            }
            float average = (float) sum/length;
            String formattedAverage = String.format("%.1f", average);
            String formatCount = Integer.toString(length);
            averageQCInstructorFeedbacks.get(key).add(formattedAverage);
            averageQCInstructorFeedbacks.get(key).add(formatCount);

        }

        return averageQCInstructorFeedbacks;
    }



	public List<EmployeeDTO> getAllAssociates(long batchId, boolean includeQuizScores, boolean includeTopicCompetencies, boolean includeQCFeedback) {
		List<BatchAssociates> associates = batchAssociatesRepository.findAllInBatchById(batchId);
		List<Long> idsList = new ArrayList<>();
		associates.forEach(batchAssociates -> idsList.add(batchAssociates.getBatchAssociatesId().getEmployeeId()));
		// Get employees from the employee service
		return employeeService.getEmployeesByListOfIds(idsList, includeQuizScores, includeTopicCompetencies, includeQCFeedback);
	}

	@Transactional
    public List<Employee> addAssociate(long batchId, List<Employee> employeeEmails){
		Batch batch = batchRepository.findById(batchId).orElse(null);
		if (batch==null) {
			// return or throw exception
			return null;
		}
		List<String> emailList = new ArrayList<>();
		employeeEmails.forEach(employee -> emailList.add(employee.getEmail()));
		List<Employee> fullEmployees = employeeService.getEmployeesByListOfEmails(emailList);

		List<BatchAssociates> associatesToAdd = new ArrayList<>();
		List<Employee> notVerified = new ArrayList<>();

		/* todo: updated this method to account for unverified users.  They will NOT be added to the batch if they are still
		    unverified, however they are still prompted with another verification email.
        */
		fullEmployees.forEach(employee -> {
                if(!employee.getRole().equals("GUEST")) {
                    associatesToAdd.add(new BatchAssociates(new BatchAssociates.BatchAssociatesId(employee.getId(), batch)));
                } else {
                    notVerified.add(employee);
                }
		});
		associatesToAdd.forEach(batchAssociate -> batchAssociatesRepository.save(batchAssociate));
        employeeService.sendBatchEmails(emailList, batch.getName(), batch.getDescription(), batch.getLocation(), batch.getTrainerId());

        //todo: now returns unverified users so it can be displayed on the front end/to instructor so they can reach out
		return notVerified;
    }


    @Transactional
    public void deleteAssociate(long batchId, long empId){
        batchAssociatesRepository.deleteByEmployeeId(empId);
    }

}
