package backend.player.dbus;

import java.util.Map;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.types.DBusListType;
import org.freedesktop.dbus.types.DBusMapType;

@DBusInterfaceName("org.freedesktop.DBus.Properties")
public interface Properties<T> extends DBusInterface {

	public void PropertiesChanged(String interface_name, Variant<Map> changed_properties, DBusListType invalidated_properties);
	
	public Variant<T> Get(String interface_name, String metadata);
	
	public Variant<Map> GetAll(String interface_name);
	
	public void Set(String interface_name, String metadata, Variant<T> value);
	
}
