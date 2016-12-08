package ch.uzh.ifi.seal.monolith2microservices.models.couplings;

public class LogicalCoupling extends BaseCoupling{
	
	private int startTimestamp;
	
	private int endTimestamp;
	
	private String hash;

	public LogicalCoupling(String firstFileName, String secondFileName, double score) {
		super(firstFileName, secondFileName, score);
	}

	public void incrementScore(){
		this.score++;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public int getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(int startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public int getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(int endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	@Override
	public String toString() {
		return "LogicalCoupling [classes=" + firstFileName + "|" + secondFileName + ", score=" + score + ", hash=" + hash + "]";
	}
	
}
