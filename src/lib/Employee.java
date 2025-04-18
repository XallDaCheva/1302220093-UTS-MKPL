package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String address;
    private int yearJoined;
    private int monthJoined;
    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;
    private boolean isForeigner;
    private Spouse spouse;
    private List<Child> children;

    private static final int BASE_SALARY_GRADE_1 = 3000000;
    private static final int BASE_SALARY_GRADE_2 = 5000000;
    private static final int BASE_SALARY_GRADE_3 = 7000000;
    private static final double FOREIGNER_SALARY_MULTIPLIER = 1.5;

    public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, boolean isForeigner) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.address = address;
        this.yearJoined = yearJoined;
        this.monthJoined = monthJoined;
        this.isForeigner = isForeigner;
        this.children = new LinkedList<>();
    }

    public void setMonthlySalary(int grade) {
        switch (grade) {
            case 1:
                monthlySalary = BASE_SALARY_GRADE_1;
                break;
            case 2:
                monthlySalary = BASE_SALARY_GRADE_2;
                break;
            case 3:
                monthlySalary = BASE_SALARY_GRADE_3;
                break;
            default:
                monthlySalary = 0;
        }
        if (isForeigner) {
            monthlySalary *= FOREIGNER_SALARY_MULTIPLIER;
        }
    }

    public void setOtherMonthlyIncome(int income) {
        this.otherMonthlyIncome = income;
    }

    public void setAnnualDeductible(int deductible) {
        this.annualDeductible = deductible;
    }

    public void setSpouse(String spouseName, String spouseIdNumber) {
        this.spouse = new Spouse(spouseName, spouseIdNumber);
    }

    public void addChild(String childName, String childIdNumber) {
        this.children.add(new Child(childName, childIdNumber));
    }

    private int calculateMonthsWorkedInYear() {
        LocalDate currentDate = LocalDate.now();
        if (currentDate.getYear() == yearJoined) {
            return currentDate.getMonthValue() - monthJoined;
        }
        return 12;
    }

    public int getAnnualIncomeTax() {
        int monthsWorked = calculateMonthsWorkedInYear();
        boolean isMarried = (spouse != null);
        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthsWorked, annualDeductible, isMarried, children.size());
    }
}
