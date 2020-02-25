package playerStatus;

import java.lang.Math;
public class PlayerStatus {

	private final String nickname;
	private int score;
	private int lives;
	private int health;
	private String weaponInHand;
	private double positionX;
	private double positionY;
	static String gameName;

	//constructors
	public PlayerStatus(String nickname,int lives,int score) {
		this.nickname=nickname;
		this.lives=lives;
		this.score=score;
	}
	public PlayerStatus(String nickname,int lives) {
		this(nickname,lives,0);
	}
	public PlayerStatus(String nickname) {
		this(nickname,0);
	}
	public PlayerStatus() {
		this("");
	}

	//get-eri && set-eri
	public String getNickname() {
		return nickname;
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	public String getWeaponInHand() {
		return weaponInHand;
	}
	public boolean setWeaponInHand(String weaponInHand) {
		int weaponPrice=0;
		if(weaponInHand.equals("knife") && this.getScore()>=1000) {
			this.weaponInHand = "knife";
			weaponPrice=1000;
		}
		else if(weaponInHand.equals("sniper") && this.getScore()>=10000) {
			this.weaponInHand = "sniper";
			weaponPrice=10000;
		}
		else if(weaponInHand.equals("kalashnikov") && this.getScore()>=1000) {
			this.weaponInHand = "kalashnikov";
			weaponPrice=20000;
		}
		if(weaponPrice!=0) {
			this.setScore(this.getScore()-weaponPrice);
			return true;
		}
		return false;
		
	}

	public double getPositionX() {
		return positionX;
	}
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	
	protected static void setGameName(String gameName) {
		PlayerStatus.gameName = gameName;
	}
	public static String getGameName() {
		return gameName;
	}

	//regulile jocului
	public void controlHealth() {
		if(this.getHealth()<=0) {
			this.setHealth(100);
			this.setLives(this.getLives()-1);
		}
		else if(this.getHealth()>100)
			this.setHealth(100);
	}

	public void controlLives() {
		if(this.getLives()==0)
			System.out.println("Game Over");
	}

	
	private double calculateDistance(PlayerStatus otherPlayer) {
		double distance;
		distance=Math.sqrt(Math.pow((this.getPositionX()-otherPlayer.getPositionX()),2)+Math.pow((this.getPositionY()-otherPlayer.getPositionY()),2));
		return distance;
	}

	private String calculateWeaponsDistanceGreater(PlayerStatus otherPlayer) {
		String winnerName="";
		if(this.getWeaponInHand().equals("sniper") && !otherPlayer.getWeaponInHand().equals("sniper")) {
			//System.out.println("Player "+this.getNickname()+" has won the fight");
			winnerName=this.getNickname();
		}
		else if(otherPlayer.getWeaponInHand().equals("sniper") && !this.getWeaponInHand().equals("sniper")) {
			//System.out.println("Player "+otherPlayer.getNickname()+" has won the fight");
			winnerName=otherPlayer.getNickname();
		}
		else if(this.getWeaponInHand().equals("kalashnikov") && otherPlayer.getWeaponInHand().equals("knife")) {
			//System.out.println("Player "+this.getNickname()+" has won the fight");
			winnerName=this.getNickname();
		}
		else  if(otherPlayer.getWeaponInHand().equals("kalashnikov") && this.getWeaponInHand().equals("knife")) {
			//System.out.println("Player "+otherPlayer.getNickname()+" has won the fight");
			winnerName=otherPlayer.getNickname();
		}
		return winnerName;
	}

	private String calculateWeaponsLessDistance(PlayerStatus otherPlayer) {
		String winnerName="";
		if(this.getWeaponInHand().equals("kalashnikov") && !otherPlayer.getWeaponInHand().equals("kalashnikov")) {
			//System.out.println("Player "+this.getNickname()+" has won the fight");
			winnerName=this.getNickname();
		}
		else if(otherPlayer.getWeaponInHand().equals("kalashnikov") && !this.getWeaponInHand().equals("kalashnikov")) {
			//System.out.println("Player "+otherPlayer.getNickname()+" has won the fight");
			winnerName=otherPlayer.getNickname();
			}
		else if(this.getWeaponInHand().equals("sniper") && otherPlayer.getWeaponInHand().equals("knife")) {
			//System.out.println("Player "+this.getNickname()+" has won the fight");
			winnerName=this.getNickname();
		}
		else  if(otherPlayer.getWeaponInHand().equals("sniper") && this.getWeaponInHand().equals("knife")) {
			//System.out.println("Player "+otherPlayer.getNickname()+" has won the fight");
			winnerName=otherPlayer.getNickname();
		}
		return winnerName;
		
	}

	private String calculateProbability(PlayerStatus otherPlayer) {
		double p1,p2;
		String winnerName;
		p1=3*this.getHealth()+this.getScore()/1000/4;
		p2=3*otherPlayer.getHealth()+otherPlayer.getScore()/1000/4;
		if(p1>=p2)
			winnerName=this.getNickname();
		else winnerName=otherPlayer.getNickname();
		return winnerName;
	}

	private String duel(PlayerStatus otherPlayer) {
		if(this.getWeaponInHand().equals(otherPlayer.getWeaponInHand())) {
			return calculateProbability(otherPlayer);
		}
		else {
			if(calculateDistance(otherPlayer)>1000) {
				return calculateWeaponsDistanceGreater(otherPlayer);
			}
			else {
				return calculateWeaponsLessDistance(otherPlayer);
			}
		}

	}

	//comportamentul jocului
	
	public void findArtifact(int artifactCode) {
		if(isPerfect(artifactCode)==true) {
			this.setScore(this.getScore()+5000);
			this.setLives(this.getLives()+1);
			this.setHealth(100);
		}
		else if(isPrime(artifactCode)==true) {
				this.setScore(this.getScore()+1000);
				this.setLives(this.getLives()+2);
				this.setHealth(this.getHealth()+25);
				controlHealth();
		}
			else if(isEven(artifactCode) && isSumDivBy3(artifactCode)) {
				this.setScore(this.getScore()-3000);
				this.setHealth(this.getHealth()+25);
				controlHealth();
			}
				else {
					this.setScore(this.getScore()+artifactCode);
				} 
	}

	private boolean isPerfect(int n) {
		int i,s=0;
		for(i=1;i<=Math.sqrt(n);i++)
			if(n%i==0)
				s+=i;
		if(n==s)
			return true;
		return false;
	}
	private boolean isPrime(int n) {
		for(int i=2;i<=Math.sqrt(n);i++)
			if(n%i==0)
				return false;
		return true;
	}
	
	private boolean isEven(int n) {
		if(n%2==0)
			return true;
		return false;
	}
	private boolean isSumDivBy3(int n) {
		int s=0;
		while(n!=0)
		{
			s+=n%10;
			n/=10;
		}
		if(s%3==0)
			return true;
		return false;
	}
	
	public void movePlayerTo(double positionX, double positionY) {
		this.positionX=positionX;
		this.positionY=positionY;
	}
	
	public boolean shouldAttackOponent(PlayerStatus oponent) {
		if(!(duel(oponent).equals(this.getNickname())))
			return false;
		return true;
	}
	
	
}
