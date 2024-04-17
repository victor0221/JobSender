/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package jobsender;

/**
 *
 * @author usula
 */
public interface IJob {
    public void setJobName(String name);
    public void setJobTime(float time);
    public String getJobName();
    public float getJobTime();
    public void setJob(String name, float time);
    public Object[] getJob();
    
}
