package dev.batch.services;

import dev.batch.dto.*;
import dev.batch.models.Batch;
import dev.batch.models.BatchAssociates;
import dev.batch.repositories.BatchAssociatesRepository;
import dev.batch.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        //quizAverages for batch
        Map<Long, List<String>> quizAverages = getQuizAveragesInfo(id);
        //TODO: quiz averages and competencies each make a call. Can be condensed into 1 call
		//Can get quiz and topic names in a single / two calls to curriculum here

        Batch batch = getBasicBatchInfo(id);
        //go to employee service and call for trainer


		//topic competencies for batch
        Map<Long, List<String>> tagAverages = getTopicCompetencyAveragesInfo(id);

        return new BatchResponse(batch, quizAverages, tagAverages);
    }

    public Batch getBasicBatchInfo(Long id) {
        return batchRepository.findById(id).orElse(null);

    }

    public Map<Long, List<String>> getQuizAveragesInfo(Long id) {

    	List<EmployeeDTO> employeeDTOS = getAllAssociates(id, true, false);
        List<EmployeeQuizScore> empQuiz = new LinkedList<>(); // Fetch from employee service
		employeeDTOS.forEach(employeeDTO -> empQuiz.addAll(employeeDTO.getQuizScores()));

        //Need to define comparator still
        Map<Long, List<String>> averageScoreForQuizzes = new TreeMap<>();
        Map<Long, List<Float>> scoresForQuiz = new TreeMap<>();
        for (int i = 0; i < empQuiz.size(); i++) {
            Long quizId = empQuiz.get(i).getId().getQuizId();
            // need to make sure to get quiz name from curriculum service
			// TODO: makes a request for each quiz. Can get all names at once
            String quizName = curriculumService.getQuizById(empQuiz.get(i).getId().getQuizId()).getName();
            if (!(scoresForQuiz.containsKey(quizId))){
                scoresForQuiz.put(quizId, new ArrayList<>());
                scoresForQuiz.get(quizId).add(empQuiz.get(i).getScore());
                averageScoreForQuizzes.put(quizId, new ArrayList<>());
                averageScoreForQuizzes.get(quizId).add(quizName);
            }
            else {
                scoresForQuiz.get(quizId).add(empQuiz.get(i).getScore());
            }
        }

        for (Long key : scoresForQuiz.keySet()) {
            int length = scoresForQuiz.get(key).size();
            int sum = 0;
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

    public Map<Long, List<String>> getTopicCompetencyAveragesInfo(Long id) {

		List<EmployeeDTO> employeeDTOS = getAllAssociates(id, false, true);
        List<EmployeeTopicCompetency> employeeTopicCompetencies = new ArrayList<>(); // Fetch from employee service
		employeeDTOS.forEach(employeeDTO -> employeeTopicCompetencies.addAll(employeeDTO.getTopicCompetencies()));

        //Need to define comparator still
        Map<Long, List<String>> averageTopicCompetencies = new TreeMap<>();
        Map<Long, List<Float>> competenciesForTechnology = new TreeMap<>();
        for (int i = 0; i < employeeTopicCompetencies.size(); i++) {
            Long topicId = employeeTopicCompetencies.get(i).getId().getTopicId();
            // need to make sure to get topic name from curriculum service
            // String quizName = empQuiz.get(i).getQuiz().getName();
            if (!(competenciesForTechnology.containsKey(topicId))){
                competenciesForTechnology.put(topicId, new ArrayList<>());
                competenciesForTechnology.get(topicId).add(employeeTopicCompetencies.get(i).getCompetency());
                averageTopicCompetencies.put(topicId, new ArrayList<>());
                //averageScoreForQuizzes.get(quizId).add(quizName);
            }
            else {
                competenciesForTechnology.get(topicId).add(employeeTopicCompetencies.get(i).getCompetency());
            }
        }

        for (Long key : competenciesForTechnology.keySet()) {
            int length = competenciesForTechnology.get(key).size();
            int sum = 0;
            for (int i = 0; i < length; i++) {
                sum += competenciesForTechnology.get(key).get(i);
            }
            float average = (float) sum/length;
            String formattedAverage = String.format("%.2f", average);
            String formatCount = Integer.toString(length);
            averageTopicCompetencies.get(key).add(formattedAverage);
            averageTopicCompetencies.get(key).add(formatCount);

        }

        return averageTopicCompetencies;
    }






    /*public class SortAscendingComparatorId implements Comparator<Long> {

        @Override
        public int compare(int o1, int o2) {
            return o1.compare(o2);
        }

    }*/


	public List<EmployeeDTO> getAllAssociates(long batchId, boolean includeQuizScores, boolean includeTopicCompetencies) {
		List<BatchAssociates> associates = batchAssociatesRepository.findAllInBatchById(batchId);
		List<Long> idsList = new ArrayList<>();
		associates.forEach(batchAssociates -> idsList.add(batchAssociates.getBatchAssociatesId().getEmployeeId()));
		// Get employees from the employee service
		return employeeService.getEmployeesByListOfIds(idsList, includeQuizScores, includeTopicCompetencies);
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
		//TODO: get the employee objects from Employee Service with the emails
		// for now, only get returns (?) for people currently registered so this list may be shorter
		List<Employee> fullEmployees = new ArrayList<>();
		//fullEmployees = employeeService.getEmployeesByListOfEmails(emailList);

		List<BatchAssociates> associatesToAdd = new ArrayList<>();
		fullEmployees.forEach(employee -> associatesToAdd.add(new BatchAssociates(new BatchAssociates.BatchAssociatesId(employee.getId(), batch))));

		associatesToAdd.forEach(batchAssociate -> batchAssociatesRepository.save(batchAssociate));

		return fullEmployees;
    }

    @Transactional
    public void deleteAssociate(long batchId, long empId){
        batchAssociatesRepository.deleteByEmployeeId(empId);
    }
}
