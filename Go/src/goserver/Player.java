package goserver;

public class Player
{
   private String name;
   private boolean busy = false;
    
    Player(String name)
    {
        this.name = name;
    }
    
    public String getName()  { return name;  }
    public boolean isBusy()  { return busy;  }
    public void setBusy()    { busy = true;  }
    public void setNotBusy() { busy = false; }
}
