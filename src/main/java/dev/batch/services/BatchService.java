package dev.batch.services;

import dev.batch.dto.BatchResponse;
import dev.batch.models.Batch;
import dev.batch.repositories.BatchRepository;

public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private BatchAssociatesRepository batchAssociatesRepository;

    public BatchResponse getBatchInfoAndAveragesById(long id) {
        // Get name, description and instructor of batch -- will later be passed to BatchResponse object
        Batch batch = getBasicBatchInfo(id);

        //quizAverages for batch
        //go to employee service and call for trainer
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

        return new BatchResponse(batch, quizAverages, tagAverages);
    }

    public Batch getBasicBatchInfo(Long id) {
        return batchRepository.getBatchById(id);
    }



    public List<TagAverage> getTopicCompetencyAveragesInfo(Long id) {
        return employeeTopicRepository.findTagAveragesByBatch(id);
    }

    public class  SortAscendingComparatorId implements Comparator<String> {

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
        batchAssociatesRepository.deleteByEmployeeId(empId);
    }
}
