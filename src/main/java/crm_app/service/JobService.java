package crm_app.service;

import crm_app.entity.JobEntity;
import crm_app.repository.JobRepository;

import java.util.List;

public class JobService {
    private JobRepository jobRepository = new JobRepository();
    private JobRepository deleteJobRepository = new JobRepository();
    private JobRepository addJobRepository = new JobRepository();
    private JobRepository updateJobRepository = new JobRepository();

    public List<JobEntity> getAllJob(){
        return jobRepository.findAll();
    }

    public boolean deleteJobById(int id) {

        int count = deleteJobRepository.deleteById(id);

        return count > 0 ;
    }

    public boolean insertJob(String name, String start_date, String end_date) {
        return addJobRepository.insert(name, start_date, end_date) > 0;
    }

    public JobEntity getJobById(int id) {
        return jobRepository.updateJobById(id);
    }
    public void updateRole(int id, String name, String start_date, String end_date) {
        updateJobRepository.updateJob(id, name, start_date, end_date);
    }
}
