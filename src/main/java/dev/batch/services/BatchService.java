package dev.batch.services;

import dev.batch.dto.BatchResponse;
import dev.batch.dto.Employee;
import dev.batch.models.Batch;
import dev.batch.models.BatchAssociates;
import dev.batch.repositories.BatchAssociatesRepository;
import dev.batch.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class BatchService {

	@Autowired
	private BatchRepository batchRepository;
	@Autowired
	private BatchAssociatesRepository batchAssociatesRepository;
	@Autowired
	private EmployeeService employeeService;

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

	public List<Employee> getAllAssociates(long batchId) {
		List<BatchAssociates> associates = batchAssociatesRepository.findAllInBatch(batchId);
		List<Long> idsList = new ArrayList<>();
		associates.forEach(batchAssociates -> idsList.add(batchAssociates.getBatchAssociatesId().getEmployeeId()));
		// Get employees from the employee service
		return employeeService.getEmployeesByListOfIds(idsList);
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
		// get the employee objects from Employee Service with the emails
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
