package test.test;

public class EsbExamVo {
	
	//private String serviceCode;特征码    对应申请明细的id
	
    private String patientID;  //患者ID
    private String orderID;    // 申请单号
    private String orderStatus;
    private String name;  // 患者姓名
    private String birthday; // 患者出生日期     以秒为单位
    private String sex;  // 患者性别
    private String age;  // 患者年龄
    private String ageUnit; // 患者年龄单位
    private String fromDept; // 申请科室
    private String fromDoctor; // 申请医生
    private String inPatientNO;  //住院号
    private String outPatientNO; //门诊号
    private String sickBedNO; //床号
    private String caseHistory; // 病历摘要
    private String clinicDiagnosis; //临床诊断
    private String visceras; //检查部位（多个部位以英文半角分号“;”分隔）
    private String fee; //费用
    private String reqDate; //申请时间
    private String isInPatient; //是否住院（住院：Y其他：N）
    private String catalog; //病人来源:门诊、住院、体检等
    private String diagnosisNO; //诊疗卡号
    private String moduleName; //模设备型  超声:UIS,放射:RIS,内窥镜:EIS，病理:PIS
    private String machineType; //设备类型（彩超、黑白超、腹腔镜、宫腔镜、CT、DX、MR、CR、RF、DSA、SC牙片,等）
    private String status; //申请状态(已发起,已终止,已完成)
    
    
	public String getPatientID() {
		return patientID;
	}
	public String getOrderID() {
		return orderID;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public String getName() {
		return name;
	}
	public String getBirthday() {
		return birthday;
	}
	public String getSex() {
		return sex;
	}
	public String getAge() {
		return age;
	}
	public String getAgeUnit() {
		return ageUnit;
	}
	public String getFromDept() {
		return fromDept;
	}
	public String getFromDoctor() {
		return fromDoctor;
	}
	public String getInPatientNO() {
		return inPatientNO;
	}
	public String getOutPatientNO() {
		return outPatientNO;
	}
	public String getSickBedNO() {
		return sickBedNO;
	}
	public String getCaseHistory() {
		return caseHistory;
	}
	public String getClinicDiagnosis() {
		return clinicDiagnosis;
	}
	public String getVisceras() {
		return visceras;
	}
	public String getFee() {
		return fee;
	}
	public String getReqDate() {
		return reqDate;
	}
	public String getIsInPatient() {
		return isInPatient;
	}
	public String getCatalog() {
		return catalog;
	}
	public String getDiagnosisNO() {
		return diagnosisNO;
	}
	public String getModuleName() {
		return moduleName;
	}
	public String getMachineType() {
		return machineType;
	}
	public String getStatus() {
		return status;
	}
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setAgeUnit(String ageUnit) {
		this.ageUnit = ageUnit;
	}
	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}
	public void setFromDoctor(String fromDoctor) {
		this.fromDoctor = fromDoctor;
	}
	public void setInPatientNO(String inPatientNO) {
		this.inPatientNO = inPatientNO;
	}
	public void setOutPatientNO(String outPatientNO) {
		this.outPatientNO = outPatientNO;
	}
	public void setSickBedNO(String sickBedNO) {
		this.sickBedNO = sickBedNO;
	}
	public void setCaseHistory(String caseHistory) {
		this.caseHistory = caseHistory;
	}
	public void setClinicDiagnosis(String clinicDiagnosis) {
		this.clinicDiagnosis = clinicDiagnosis;
	}
	public void setVisceras(String visceras) {
		this.visceras = visceras;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public void setIsInPatient(String isInPatient) {
		this.isInPatient = isInPatient;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public void setDiagnosisNO(String diagnosisNO) {
		this.diagnosisNO = diagnosisNO;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
