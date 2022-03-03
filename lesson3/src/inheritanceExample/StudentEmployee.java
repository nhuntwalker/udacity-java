package inheritanceExample;

public class StudentEmployee extends Student {
    private double rateOfPayPerHour;
    private String employeeId;

    public StudentEmployee (String firstName, String lastName, String studentId, double rateOfPay, String employeeId){
        super(firstName, lastName, studentId);
        this.rateOfPayPerHour = rateOfPay;
        this.employeeId = employeeId;
    }
    public double getRateOfPayPerHour(){
        return rateOfPayPerHour;
    }
    public String getEmployeeId(){
        return employeeId;
    }
    public void setRateOfPayPerHour(double rateOfPayPerHour) {
        this.rateOfPayPerHour = rateOfPayPerHour;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString(){
        return super.toString() + " employee ID: " + employeeId + " Pay: " + rateOfPayPerHour;
    }
}
