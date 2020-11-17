package models.service;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService ops;
	
	
	
	public ContractService(OnlinePaymentService ops) {
		this.ops = ops;
	}



	public void processContract(Contract contract, int months) {
		double basicQuota = contract.getTotalValue() / months;
		for(int i = 1; i <= months; i++) {
			double updatedQuota = basicQuota + ops.interest(basicQuota, i);
			double fullQuota = updatedQuota + ops.paymentFee(updatedQuota);
			
			Date dueDate = addMonths(contract.getDate(), i);
			contract.getInstallments().add(new Installment(dueDate, fullQuota));
		}
		
		
	}
	
	private Date addMonths(Date date, int addM) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, addM);
		return calendar.getTime();
	}

}
