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
	private int dayJoined;
	private int monthWorkingInYear;
	
	private boolean isForeigner;
	private boolean gender; //true = Laki-laki, false = Perempuan
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;
		
		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}
	
	private static final int BASE_SALARY_GRADE_1 = 3000000;
	private static final int BASE_SALARY_GRADE_2 = 5000000;
	private static final int BASE_SALARY_GRADE_3 = 7000000;
	private static final double FOREIGNER_SALARY_MULTIPLIER = 1.5;
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
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
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = idNumber;
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
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
	    boolean isMarried = !spouseIdNumber.equals("");
	    return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthsWorked, annualDeductible, isMarried, childIdNumbers.size());
	}
}
