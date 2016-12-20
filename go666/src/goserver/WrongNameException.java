package goserver;



class WrongNameException extends Exception
{    
    /**
     * 
     */
    private static final long serialVersionUID = -6072420595132572610L;

    public WrongNameException(String message)
    {
        super(message);
    }
}
