package EventManagement;

/**
* EventManagement/managerInterfaceHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from EventManagement.idl
* Sunday, July 7, 2019 1:17:15 PM EDT
*/

public final class managerInterfaceHolder implements org.omg.CORBA.portable.Streamable
{
  public EventManagement.managerInterface value = null;

  public managerInterfaceHolder ()
  {
  }

  public managerInterfaceHolder (EventManagement.managerInterface initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = EventManagement.managerInterfaceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    EventManagement.managerInterfaceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return EventManagement.managerInterfaceHelper.type ();
  }

}
