package dev.batch.services;

import dev.batch.dto.BatchResponse;
import dev.batch.dto.Employee;
import dev.batch.dto.EmployeeQuizScores;
import dev.batch.dto.EmployeeTopicCompetency;
import dev.batch.models.Batch;
import dev.batch.repositories.BatchRepository;

import java.util.*;

public class BatchService {

    private BatchRepository batchRepository;

    public BatchResponse getBatchInfoAndAveragesById(long id) {
        // Get name, description and instructor of batch -- will later be passed to BatchResponse object
        Batch batchSummary = getBasicBatchInfo(id);

        //quizAverages for batch
        Map<Long, List<String>> quizAverages = getQuizAveragesInfo(id);


        //topic competencies for batch
        Map<Long, List<String>> tagAverages = getTopicCompetencyAveragesInfo(id);

        return new BatchResponse(batchSummary, quizAverages, tagAverages);
    }

    public Batch getBasicBatchInfo(Long id) {
        logger.info("Hello");
        return batchRepository.getById(id);
    }

    public Map<Long, List<String>> getQuizAveragesInfo(Long id) {

        List<EmployeeQuizScores> empQuiz; // Fetch from employee service
        //Need to define comparator still
        Map<Long, List<String>> averageScoreForQuizzes = new TreeMap<>();
        Map<Long, List<Float>> scoresForQuiz = new TreeMap<>();
        for (int i = 0; i < empQuiz.size(); i++) {
            Long quizId = empQuiz.get(i).getQuizId();
            // need to make sure to get quiz name from curriculum service
           // String quizName = empQuiz.get(i).getQuiz().getName();
            if (!(scoresForQuiz.containsKey(quizId))){
                scoresForQuiz.put(quizId, new ArrayList<>());
                scoresForQuiz.get(quizId).add(empQuiz.get(i).getScore());
                averageScoreForQuizzes.put(quizId, new ArrayList<>());
                //averageScoreForQuizzes.get(quizId).add(quizName);
            }
            else {
                scoresForQuiz.get(quizId).add(empQuiz.get(i).getScore());
                //averageScoreForQuizzes.get(quizId).add(quizName);
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

        List<EmployeeTopicCompetency> employeeTopicCompetencies; // Fetch from employee service
        //Need to define comparator still
        Map<Long, List<String>> averageTopicCompetencies = new TreeMap<>();
        Map<Long, List<Float>> competenciesForTechnology = new TreeMap<>();
        for (int i = 0; i < employeeTopicCompetencies.size(); i++) {
            Long topicId = employeeTopicCompetencies.get(i).getTopicId();
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
                //averageScoreForQuizzes.get(quizId).add(quizName);
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






    public class SortAscendingComparatorId implements Comparator<Integer> {

        @Override
        public int compare(int o1, int o2) {
            return o1.compare(o2);
        }

    }

    public List<Employee> getAllAssociates(long batchId){
        Batch batch = batchRepository.getBatchById(batchId);
        if(batch!=null){
            return batch.getAssociates();
            //
        }
        return new ArrayList<>();
    }

    public List<Employee> addAssociate(long batchId, List<Employee> employees){
        Batch batch = batchRepository.getBatchById(batchId);
        List<Employee> newAssociates = new ArrayList<>();
        if(batch!=null){

            for(Employee e : employees){
                if(e.getName()!=null){
                    newAssociates.add(e);
                }else{
                    try{
                        e = employeeRepository.findByEmail(e.getEmail());// reassign the employee object with the one found by email stored within
                        newAssociates.add(e);
                    } catch (EntityNotFoundException exception){
                        employees.remove(e);
                    }
                }


            }
            batch.setAssociates(newAssociates);
            batchRepository.save(batch);
            return employees;
        }
        return new ArrayList<>();
    }

    @Transactional
    public void deleteAssociate(long batchId, long empId){
        Batch batch = batchRepository.getBatchById(batchId);
        Employee emp = employeeRepository.getOne(empId);
        if(batch!=null){

            batch.removeAssociate(emp);
            batchRepository.save(batch);
        }
    }



}
