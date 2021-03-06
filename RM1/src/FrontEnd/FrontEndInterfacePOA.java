package FrontEnd;


/**
* FrontEnd/FrontEndInterfacePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from FrontEnd.idl
* Sunday, July 14, 2019 6:28:55 PM EDT
*/

public abstract class FrontEndInterfacePOA extends org.omg.PortableServer.Servant
 implements FrontEnd.FrontEndInterfaceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("addEvent", new java.lang.Integer (0));
    _methods.put ("removeEvent", new java.lang.Integer (1));
    _methods.put ("listEventAvailability", new java.lang.Integer (2));
    _methods.put ("eventBooking", new java.lang.Integer (3));
    _methods.put ("cancelBooking", new java.lang.Integer (4));
    _methods.put ("getBookingSchedule", new java.lang.Integer (5));
    _methods.put ("swapEvent", new java.lang.Integer (6));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // FrontEnd/FrontEndInterface/addEvent
       {
         String managerId = in.read_string ();
         String eventId = in.read_string ();
         String eventType = in.read_string ();
         String eventCapacity = in.read_string ();
         String $result = null;
         $result = this.addEvent (managerId, eventId, eventType, eventCapacity);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // FrontEnd/FrontEndInterface/removeEvent
       {
         String managerId = in.read_string ();
         String eventId = in.read_string ();
         String eventType = in.read_string ();
         String $result = null;
         $result = this.removeEvent (managerId, eventId, eventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // FrontEnd/FrontEndInterface/listEventAvailability
       {
         String managerId = in.read_string ();
         String eventType = in.read_string ();
         String $result = null;
         $result = this.listEventAvailability (managerId, eventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // FrontEnd/FrontEndInterface/eventBooking
       {
         String customerId = in.read_string ();
         String eventId = in.read_string ();
         String eventType = in.read_string ();
         String $result = null;
         $result = this.eventBooking (customerId, eventId, eventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // FrontEnd/FrontEndInterface/cancelBooking
       {
         String customerId = in.read_string ();
         String eventId = in.read_string ();
         String eventType = in.read_string ();
         String $result = null;
         $result = this.cancelBooking (customerId, eventId, eventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // FrontEnd/FrontEndInterface/getBookingSchedule
       {
         String customerId = in.read_string ();
         String $result = null;
         $result = this.getBookingSchedule (customerId);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 6:  // FrontEnd/FrontEndInterface/swapEvent
       {
         String customerId = in.read_string ();
         String newEventId = in.read_string ();
         String newEventType = in.read_string ();
         String oldEventId = in.read_string ();
         String oldEventType = in.read_string ();
         String $result = null;
         $result = this.swapEvent (customerId, newEventId, newEventType, oldEventId, oldEventType);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:FrontEnd/FrontEndInterface:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public FrontEndInterface _this() 
  {
    return FrontEndInterfaceHelper.narrow(
    super._this_object());
  }

  public FrontEndInterface _this(org.omg.CORBA.ORB orb) 
  {
    return FrontEndInterfaceHelper.narrow(
    super._this_object(orb));
  }


} // class FrontEndInterfacePOA
