package gogame;

public class Player
{
    String name;
   
    public Player(String name)
    {
        this.name = name;
      
    }
    
    public String getName() { return name; }
   

	public boolean isBusy() {
		// TODO Auto-generated method stub
		return false;
	}
    
	public void setName(String name){
		this.name = name;
	}
	

	
	public String toString(){
		return name;
		
	}
    

}
