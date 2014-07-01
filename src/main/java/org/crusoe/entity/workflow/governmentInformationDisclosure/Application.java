package org.crusoe.entity.workflow.governmentInformationDisclosure;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "workflow_governmentInformationDisclosure_application")
public class Application {
	private long id;
	private String userLoginName;
	private Date createTime;
	private int numberOfApplicationOnTheSpot;
	private int numberOfApplicationFromNetworkOrEmail;
	private int numberOfApplicationFromMailOrFax;
	private int numberOfApplicationFromOthers;

	private int numberOfAgreedReply;
	private int numberOfDelayedReply;
	private int numberOfPartOpenAgreedReply;
	private int numberOfOpenedAndToldReply;
	private int numberOfDisagreedReply;
	private int numberOfNotBelongReply;
	private int numberOfNonExistentReply;
	private int numberOfVagueReply;
	private int numberOfOtherReply;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getNumberOfApplicationOnTheSpot() {
		return numberOfApplicationOnTheSpot;
	}

	public void setNumberOfApplicationOnTheSpot(int numberOfApplicationOnTheSpot) {
		this.numberOfApplicationOnTheSpot = numberOfApplicationOnTheSpot;
	}

	public int getNumberOfApplicationFromNetworkOrEmail() {
		return numberOfApplicationFromNetworkOrEmail;
	}

	public void setNumberOfApplicationFromNetworkOrEmail(
			int numberOfApplicationFromNetworkOrEmail) {
		this.numberOfApplicationFromNetworkOrEmail = numberOfApplicationFromNetworkOrEmail;
	}

	public int getNumberOfApplicationFromMailOrFax() {
		return numberOfApplicationFromMailOrFax;
	}

	public void setNumberOfApplicationFromMailOrFax(
			int numberOfApplicationFromMailOrFax) {
		this.numberOfApplicationFromMailOrFax = numberOfApplicationFromMailOrFax;
	}

	public int getNumberOfApplicationFromOthers() {
		return numberOfApplicationFromOthers;
	}

	public void setNumberOfApplicationFromOthers(
			int numberOfApplicationFromOthers) {
		this.numberOfApplicationFromOthers = numberOfApplicationFromOthers;
	}

	public int getNumberOfAgreedReply() {
		return numberOfAgreedReply;
	}

	public void setNumberOfAgreedReply(int numberOfAgreedReply) {
		this.numberOfAgreedReply = numberOfAgreedReply;
	}

	public int getNumberOfDelayedReply() {
		return numberOfDelayedReply;
	}

	public void setNumberOfDelayedReply(int numberOfDelayedReply) {
		this.numberOfDelayedReply = numberOfDelayedReply;
	}

	public int getNumberOfPartOpenAgreedReply() {
		return numberOfPartOpenAgreedReply;
	}

	public void setNumberOfPartOpenAgreedReply(int numberOfPartOpenAgreedReply) {
		this.numberOfPartOpenAgreedReply = numberOfPartOpenAgreedReply;
	}

	public int getNumberOfOpenedAndToldReply() {
		return numberOfOpenedAndToldReply;
	}

	public void setNumberOfOpenedAndToldReply(int numberOfOpenedAndToldReply) {
		this.numberOfOpenedAndToldReply = numberOfOpenedAndToldReply;
	}

	public int getNumberOfDisagreedReply() {
		return numberOfDisagreedReply;
	}

	public void setNumberOfDisagreedReply(int numberOfDisagreedReply) {
		this.numberOfDisagreedReply = numberOfDisagreedReply;
	}

	public int getNumberOfNotBelongReply() {
		return numberOfNotBelongReply;
	}

	public void setNumberOfNotBelongReply(int numberOfNotBelongReply) {
		this.numberOfNotBelongReply = numberOfNotBelongReply;
	}

	public int getNumberOfNonExistentReply() {
		return numberOfNonExistentReply;
	}

	public void setNumberOfNonExistentReply(int numberOfNonExistentReply) {
		this.numberOfNonExistentReply = numberOfNonExistentReply;
	}

	public int getNumberOfVagueReply() {
		return numberOfVagueReply;
	}

	public void setNumberOfVagueReply(int numberOfVagueReply) {
		this.numberOfVagueReply = numberOfVagueReply;
	}

	public int getNumberOfOtherReply() {
		return numberOfOtherReply;
	}

	public void setNumberOfOtherReply(int numberOfOtherReply) {
		this.numberOfOtherReply = numberOfOtherReply;
	}
}
