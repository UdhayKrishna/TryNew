import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankDepositAccountOrganizer {
	
	public static void main(String args[]) throws FileNotFoundException, BankOrganizerException{
		BankDepositAccountOrganizer b = new BankDepositAccountOrganizer();
		Map<String, List<ParentAccountVO>> resultMap  = processBankDepositData("C:\\Users\\Udhayakumar\\Desktop\\accountdetails.txt");
		System.out.println(resultMap);
		
	}
	

	public static Map<String, List<ParentAccountVO>> processBankDepositData(String filePath)
			throws BankOrganizerException, FileNotFoundException {

		Map<String, List<ParentAccountVO>> resultMap = new HashMap<String, List<ParentAccountVO>>();

		try {
			BankDepositAccountOrganizer bao = new BankDepositAccountOrganizer();
			SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
			String info = "";
			File file = new File(filePath);
			if (file.exists()) {
				BufferedReader buffReader = new BufferedReader(new FileReader(file));

				List<ParentAccountVO> data1 = new ArrayList<ParentAccountVO>();
				List<ParentAccountVO> data2 = new ArrayList<ParentAccountVO>();
				List<ParentAccountVO> data3 = new ArrayList<ParentAccountVO>();

				try {
					while ((info = buffReader.readLine()) != null) {

						String[] infoArray = info.split(",");
						if (validateData(infoArray)) {
							ParentAccountVO pa = new ParentAccountVO();
							List<LinkedDepositVO> lst = new ArrayList<LinkedDepositVO>();
							LinkedDepositVO ld = new LinkedDepositVO();
							String ParentAccountNumber = infoArray[0];
							String Name = infoArray[1];
							String TypeOfaccount = infoArray[2];
							String LinkedDepositAccNumber = infoArray[3];
							String dAmt = infoArray[4];
							String dDate = infoArray[5];
							String mDate = infoArray[6];

							int DepositAmount = Integer.parseInt(dAmt);
							Date DepositStartDate = formater.parse(dDate);
							Date MaturityDate = formater.parse(mDate);
							float maturityAmount = bao.calculateMaturityAmount(DepositStartDate, MaturityDate,
									DepositAmount);
							ld.setLinkedDepositNo(LinkedDepositAccNumber);
							ld.setDepositAmount(DepositAmount);
							ld.setDepositStartDate(DepositStartDate);
							ld.setDepositMaturityDate(MaturityDate);
							ld.setMaturityAmount(maturityAmount);

							boolean flag = false;
							if (infoArray[2].equals("WM")) {

								for (ParentAccountVO parentAccountVO : data1) {

									if (parentAccountVO.getParentAccNo() == Integer.parseInt(infoArray[0])) {
										flag = true;
										List<LinkedDepositVO> linkedDepositVOs = parentAccountVO.getLinkedDeposits();
										linkedDepositVOs.add(ld);
									}

								}

							}
							System.out.println("from test branch second commit");
							if (infoArray[2].equals("SAV")) {

								for (ParentAccountVO parentAccountVO : data2) {

									if (parentAccountVO.getParentAccNo() == Integer.parseInt(infoArray[0])) {
										flag = true;
										List<LinkedDepositVO> linkedDepositVOs = parentAccountVO.getLinkedDeposits();
										linkedDepositVOs.add(ld);
									}

								}

							}
							if (infoArray[2].equals("NRI")) {

								for (ParentAccountVO parentAccountVO : data3) {

									if (parentAccountVO.getParentAccNo() == Integer.parseInt(infoArray[0])) {
										flag = true;
										List<LinkedDepositVO> linkedDepositVOs = parentAccountVO.getLinkedDeposits();
										linkedDepositVOs.add(ld);
									}

								}

							}
							if (!flag) {
								lst.add(ld);
								pa.setAccType(infoArray[2]);
								pa.setName(infoArray[1]);
								pa.setParentAccNo(Integer.parseInt(infoArray[0]));
								pa.setLinkedDeposits(lst);
								if (infoArray[2].equals("WM")) {
									data1.add(pa);
								}
								if (infoArray[2].equals("SAV")) {
									data2.add(pa);
								}
								if (infoArray[2].equals("NRI")) {
									data3.add(pa);
								}
							}
							resultMap.put("WM", data1);
							resultMap.put("SAV", data2);
							resultMap.put("NRI", data3);

						} else {
							throw new BankOrganizerException("Data Validation Failed");
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else

			{
				throw new FileNotFoundException("File not found");
			}
		} catch (

		ParseException e)

		{
			throw new BankOrganizerException("File Parse exception: " + e.getMessage());
		}

		return resultMap;

		// Write your code here
	}

	private float calculateMaturityAmount(Date date1, Date date2, int depositamount) {
		float maturity_amount = 0.00f;
		int noofdays = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));

		if (noofdays >= 0 && noofdays <= 200) {
			maturity_amount = (float) (depositamount + (depositamount * 6.75 / 100));
		} else if (noofdays >= 201 && noofdays <= 400) {
			maturity_amount = (float) (depositamount + (depositamount * 7.5 / 100));
		} else if (noofdays >= 401 && noofdays <= 600) {
			maturity_amount = (float) (depositamount + (depositamount * 8.75 / 100));
		} else {
			maturity_amount = (float) (depositamount + (depositamount * 10 / 100));

		}

		return maturity_amount;

	}

	public static boolean validateData(String[] dataArray) {

		boolean validation = true;
		// All Data mandatory Validation
		if (dataArray[0].isEmpty() || dataArray[1].isEmpty() || dataArray[2].isEmpty() || dataArray[3].isEmpty()
				|| dataArray[4].isEmpty() || dataArray[4].isEmpty() || dataArray[5].isEmpty()) {

			return false;
		}
		// Account Validation
		// Is Numeric and not starts with 0

		if (dataArray[0].charAt(0) == '0') {
			return false;
		} else {
			for (char c : dataArray[0].toCharArray()) {
				if (!Character.isDigit(c)) {
					return false;
				}
			}
		}

		if (!(dataArray[2].equals("WM") || dataArray[2].equals("SAV") || dataArray[2].equals("NRI"))) {
			return false;
		}

		if (!(dataArray[3].startsWith("FD") || dataArray[3].startsWith("RD") || dataArray[3].startsWith("MUT"))) {
			return false;
		}

		if (!validateDate(dataArray[5])) {
			return false;
		}

		return validation;
	}

	private static boolean validateDate(String date) {

		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
		formater.setLenient(false);
		try {
			formater.parse(date.trim());
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

}

class ParentAccountVO {

	private int parentAccNo;
	private String name;
	private String AccType;
	// private LinkedDepositVO linkedDeposit;
	private List<LinkedDepositVO> linkedDeposits;

	public int getParentAccNo() {
		return parentAccNo;
	}

	public void setParentAccNo(int parentAccNo) {
		this.parentAccNo = parentAccNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccType() {
		return AccType;
	}

	public void setAccType(String accType) {
		AccType = accType;
	}

	public List<LinkedDepositVO> getLinkedDeposits() {
		return linkedDeposits;
	}

	public void setLinkedDeposits(List<LinkedDepositVO> linkedDeposits) {
		this.linkedDeposits = linkedDeposits;
	}

	public boolean equals(Object object) {
		boolean isEqual = false;
		ParentAccountVO otherAccount = (ParentAccountVO) object;
		if ((this.parentAccNo == otherAccount.parentAccNo) && (this.AccType.equals(otherAccount.getAccType())
				&& (this.linkedDeposits.equals(otherAccount.getLinkedDeposits())))) {
			isEqual = true;
		}
		return isEqual;
	}

	@Override
	public String toString() {
		return "ParentAccountVO [parentAccNo=" + parentAccNo + ", name=" + name + ", AccType=" + AccType
				+ ", linkedDeposits=" + linkedDeposits + "]";

		// return parentAccNo + " , " + name + " ," + AccType + " ," +
		// linkedDeposits;

	}

}

class LinkedDepositVO {

	private String linkedDepositNo;
	private int depositAmount;
	private Date depositStartDate;
	private Date depositMaturityDate;
	private float maturityAmount;

	public String getLinkedDepositNo() {
		return linkedDepositNo;
	}

	public void setLinkedDepositNo(String linkedDepositNo) {
		this.linkedDepositNo = linkedDepositNo;
	}

	public int getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(int depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Date getDepositStartDate() {
		return depositStartDate;
	}

	public void setDepositStartDate(Date depositStartDate) {
		this.depositStartDate = depositStartDate;
	}

	public Date getDepositMaturityDate() {
		return depositMaturityDate;
	}

	public void setDepositMaturityDate(Date depositMaturityDate) {
		this.depositMaturityDate = depositMaturityDate;
	}

	public float getMaturityAmount() {
		return maturityAmount;
	}

	public void setMaturityAmount(float maturityAmount) {
		this.maturityAmount = maturityAmount;
	}

	public boolean equals(Object object) {
		boolean isEquals = false;
		LinkedDepositVO depositVO = (LinkedDepositVO) object;
		if (this.linkedDepositNo.equals(depositVO.getLinkedDepositNo())
				&& (this.depositAmount == depositVO.getDepositAmount())
				&& (this.depositStartDate.equals(depositVO.getDepositStartDate()))
				&& (this.maturityAmount == depositVO.getMaturityAmount())) {
			isEquals = true;
		}
		return isEquals;
	}

	@Override
	public String toString() {

		return "LinkedDepositVO [linkedDepositNo=" + linkedDepositNo + ", depositAmount=" + depositAmount
				+ ", depositStartDate=" + depositStartDate + ", depositMaturityDate=" + depositMaturityDate
				+ ", maturityAmount=" + maturityAmount + "]";

		// return linkedDepositNo + " , " + depositAmount + " ," +
		// depositStartDate + " ," + depositMaturityDate + "," + maturityAmount;
	}

}

class BankOrganizerException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankOrganizerException(String message) {
		super(message);
	}

	public BankOrganizerException(Throwable throwable) {
		super(throwable);
	}

	public BankOrganizerException(String message, Throwable throwable) {
		super(message, throwable);
	}
}

/************************************************************/
/*
 * DO NOT CHANGE THE BELOW CLASS. THIS IS FOR VERIFYING THE CLASS NAME AND
 * METHOD SIGNATURE USING REFLECTION APIs
 */
/************************************************************/
class Validator {

	private static final Logger LOG = Logger.getLogger("Validator");

	public Validator(String filePath, String className, String methodWithExcptn) {
		validateStructure(filePath, className, methodWithExcptn);
	}

	protected final void validateStructure(String filePath, String className, String methodWithExcptn) {

		if (validateClassName(className)) {
			validateMethodSignature(methodWithExcptn, className);
		}

	}

	protected final boolean validateClassName(String className) {

		boolean iscorrect = false;
		try {
			Class.forName(className);
			iscorrect = true;
			LOG.info("Class Name is correct");

		} catch (ClassNotFoundException e) {
			LOG.log(Level.SEVERE, "You have changed either the " + "class name/package. Use the default package "
					+ "and class name as provided in the skeleton");

		} catch (Exception e) {
			LOG.log(Level.SEVERE,
					"There is an error in validating the " + "Class Name. Please manually verify that the "
							+ "Class name is same as skeleton before uploading");
		}
		return iscorrect;

	}

	protected final void validateMethodSignature(String methodWithExcptn, String className) {
		Class cls;
		try {

			String[] actualmethods = methodWithExcptn.split(",");
			boolean errorFlag = false;
			String[] methodSignature;
			String methodName = null;
			String returnType = null;

			for (String singleMethod : actualmethods) {
				boolean foundMethod = false;
				methodSignature = singleMethod.split(":");

				methodName = methodSignature[0];
				returnType = methodSignature[1];
				cls = Class.forName(className);
				Method[] methods = cls.getMethods();
				for (Method findMethod : methods) {
					if (methodName.equals(findMethod.getName())) {
						foundMethod = true;
						if ((findMethod.getExceptionTypes().length != 1)) {
							LOG.log(Level.SEVERE, "You have added/removed " + "Exception from '" + methodName
									+ "' method. " + "Please stick to the skeleton provided");
						}
						if (!(findMethod.getReturnType().getName().equals(returnType))) {
							errorFlag = true;
							LOG.log(Level.SEVERE, " You have changed the " + "return type in '" + methodName
									+ "' method. Please stick to the " + "skeleton provided");

						}

					}
				}
				if (!foundMethod) {
					errorFlag = true;
					LOG.log(Level.SEVERE, " Unable to find the given public method " + methodName
							+ ". Do not change the " + "given public method name. " + "Verify it with the skeleton");
				}

			}
			if (!errorFlag) {
				LOG.info("Method signature is valid");
			}

		} catch (Exception e) {
			LOG.log(Level.SEVERE,
					" There is an error in validating the " + "method structure. Please manually verify that the "
							+ "Method signature is same as the skeleton before uploading");
		}
	}
}
