package backend.player.dbus;

import java.util.List;
import java.util.Map;

import org.freedesktop.dbus.*;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.types.DBusListType;
import org.freedesktop.dbus.types.DBusMapType;

@DBusInterfaceName("org.freedesktop.DBus.Properties")
public interface Properties<T> extends DBusInterface {

    @DBusMemberName("PropertiesChanged")
	public static class PropertiesChanged extends DBusSignal {

        private final String iface;

        private final Map<String, Variant> propertiesChanged;

        private final List<String> propertiesRemoved;

        public PropertiesChanged(String path, String iface, Map<String, Variant> propertiesChanged,
                                 List<String> propertiesRemoved) throws DBusException {
            super(path, iface, propertiesChanged, propertiesRemoved);
            this.iface = iface;
            this.propertiesChanged = propertiesChanged;
            this.propertiesRemoved = propertiesRemoved;
        }

        public String getIface() {
            return iface;
        }

        public Map<String, Variant> getPropertiesChanged() {
            return propertiesChanged;
        }

        public List<String> getPropertiesRemoved() {
            return propertiesRemoved;
        }
    }
	
	public Variant<T> Get(String interface_name, String metadata);
	
	public Variant<Map> GetAll(String interface_name);
	
	public void Set(String interface_name, String metadata, Variant<T> value);
	
}
