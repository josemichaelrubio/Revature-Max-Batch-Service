package dev.batch.services;

import dev.batch.dto.BatchResponse;
import dev.batch.models.Batch;
import dev.batch.repositories.BatchRepository;

public class BatchService {

    private BatchRepository batchRepository;

    public BatchResponse getBatchInfoAndAveragesById(long id) {
        // Get name, description and instructor of batch -- will later be passed to BatchResponse object
        BatchSummary batchSummary = getBasicBatchInfo(id);

        //quizAverages for batch
        List<QuizAverage> quizAverages = getQuizAveragesInfo(id);
        for (int i = 0; i < quizAverages.size(); i++) {
            double average = quizAverages.get(i).getAverageScore();
            double roundedAverage = DoubleRounder.round(average, 2);
            quizAverages.get(i).setAverageScore(roundedAverage);
        }


        //topic competencies for batch
        List<TagAverage> tagAverages = getTopicCompetencyAveragesInfo(id);
        for (int i = 0; i < tagAverages.size(); i++) {
            double average = tagAverages.get(i).getAverageCompetency();
            double roundedAverage = DoubleRounder.round(average, 2);
            tagAverages.get(i).setAverageCompetency(roundedAverage);
        }

        return new BatchResponse(batchSummary, quizAverages, tagAverages);
    }

    public BatchSummary getBasicBatchInfo(Long id) {
        logger.info("Hello");
        return batchRepository.getById(id);
    }



    public List<TagAverage> getTopicCompetencyAveragesInfo(Long id) {
        return employeeTopicRepository.findTagAveragesByBatch(id);
    }

    public class SortAscendingComparatorId implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }

    }

    public List<Employee> getAllAssociates(long batchId){
        Batch batch = batchRepository.getBatchById(batchId);
        if(batch!=null){
            return batch.getAssociates();
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
