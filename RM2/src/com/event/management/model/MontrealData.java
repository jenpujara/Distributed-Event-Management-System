package com.event.management.model;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

public class MontrealData {
	HashMap<String, HashMap<String, HashMap<String, String>>> serverData;
	String serverName;
	public static ReentrantLock lockMontrealServerData;

	public HashMap<String, HashMap<String, HashMap<String, String>>> getServerData() {
		return serverData;
	}

	public void setServerData(HashMap<String, HashMap<String, HashMap<String, String>>> serverData) {
		this.serverData = serverData;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public MontrealData() {
		serverData = new HashMap<>();
		serverData.put("Conferences", new HashMap<>());
		serverData.put("Seminars", new HashMap<>());
		serverData.put("Trade Shows", new HashMap<>());
		lockMontrealServerData = new ReentrantLock();
	}

	public boolean addEvent(String eventId, String eventtype, String eventCapacity) {
		lockMontrealServerData.lock();
		if (!serverData.containsKey(eventtype)) {
			lockMontrealServerData.unlock();
			return false;
		}
		HashMap<String, HashMap<String, String>> newValue = serverData.get(eventtype);
		if (newValue.containsKey(eventId)) {
			HashMap<String, String> newList = newValue.get(eventId);
			newList.replace("capacity", newList.get("capacity"), eventCapacity);
			newList.replace("totalBooking", newList.get("totalBooking"), newList.get("totalBooking"));
			newList.replace("customerId", newList.get("customerId"), newList.get("customerId"));
			newValue.replace(eventId, serverData.get(eventtype).get(eventId), newList);
			serverData.replace(eventtype, serverData.get(eventtype), newValue);
			lockMontrealServerData.unlock();
			return true;
		} else {
			HashMap<String, String> temp = new HashMap<>();
			temp.put("capacity", eventCapacity);
			temp.put("totalBooking", "0");
			temp.put("customerId", "");
			newValue.put(eventId, temp);
			serverData.replace(eventtype, serverData.get(eventtype), newValue);
			lockMontrealServerData.unlock();
			return true;
		}
	}

	public boolean removeEvent(String eventId, String eventType) {
		lockMontrealServerData.lock();
		if (!serverData.containsKey(eventType)) {
			lockMontrealServerData.unlock();
			return false;
		}
		HashMap<String, HashMap<String, String>> newValue = serverData.get(eventType);
		if (newValue.containsKey(eventId)) {
			serverData.get(eventType).remove(eventId);
			lockMontrealServerData.unlock();
			return true;
		} else {
			lockMontrealServerData.unlock();
			return false;
		}
	}

	public String retrieveEvent(String eventType) {
		lockMontrealServerData.lock();
		// System.out.println("Event Type : " + eventType);
		if (serverData.containsKey(eventType)) {
			HashMap<String, HashMap<String, String>> temp = serverData.get(eventType);
			if (temp.size() == 0) {
				// System.out.println("No Events Found");
				lockMontrealServerData.unlock();
				return "";
			} else {
				StringBuilder str = new StringBuilder();
				for (Entry<String, HashMap<String, String>> entry : temp.entrySet()) {
					if (Integer.parseInt(entry.getValue().get("capacity")) >= Integer
							.parseInt(entry.getValue().get("totalBooking")))
						str.append(entry.getKey() + " " + (Integer.parseInt(entry.getValue().get("capacity"))
								- Integer.parseInt(entry.getValue().get("totalBooking"))) + ",");
				}
				lockMontrealServerData.unlock();
				return str.toString().trim();
			}
		} else {
			// System.out.println("No Event Type Found");
			lockMontrealServerData.unlock();
			return "";
		}
	}

	public boolean bookEvent(String customerID, String eventId, String eventType) {
		lockMontrealServerData.lock();
		if (serverData.containsKey(eventType)) {
			HashMap<String, HashMap<String, String>> typeData = serverData.get(eventType);
			if (typeData.size() == 0) {
				// System.out.println("No Events Found");
				lockMontrealServerData.unlock();
				return false;
			} else {
				if (typeData.containsKey(eventId)) {
					HashMap<String, String> currentEvent = typeData.get(eventId);
					if (Integer.parseInt(currentEvent.get("capacity")) == Integer
							.parseInt(currentEvent.get("totalBooking"))) {
						lockMontrealServerData.unlock();
						return false;
					} else {
						StringBuilder customers = new StringBuilder(currentEvent.get("customerId"));
						if (currentEvent.get("customerId").contains(customerID)) {
							lockMontrealServerData.unlock();
							return false;
						}
						customers.append(customerID.trim());
						currentEvent.replace("customerId", customers.toString().trim() + ",");
						currentEvent.replace("capacity", currentEvent.get("capacity"),
								Integer.toString(Integer.parseInt(currentEvent.get("capacity"))));
						currentEvent.replace("totalBooking", currentEvent.get("totalBooking"),
								Integer.toString(Integer.parseInt(currentEvent.get("totalBooking")) + 1));
						typeData.replace(eventId, typeData.get(eventId), currentEvent);
						serverData.replace(eventType, serverData.get(eventType), typeData);
						lockMontrealServerData.unlock();
						return true;
					}
				} else {
					lockMontrealServerData.unlock();
					return false;
				}
			}
		} else {
			// System.out.println("No Event Type Found");
			lockMontrealServerData.unlock();
			return false;
		}
	}

	public boolean removeEvent(String customerID, String eventId, String eventType) {
		lockMontrealServerData.lock();
		if (serverData.containsKey(eventType)) {
			HashMap<String, HashMap<String, String>> typeData = serverData.get(eventType);
			if (typeData.size() == 0) {
				// System.out.println("No Events Found");
				lockMontrealServerData.unlock();
				return false;
			} else {
				if (typeData.containsKey(eventId)) {
					HashMap<String, String> currentEvent = typeData.get(eventId);
					if (Integer.parseInt(currentEvent.get("totalBooking")) == 0) {
						lockMontrealServerData.unlock();
						return false;
					} else {
						StringBuilder customers = new StringBuilder(currentEvent.get("customerId"));
						if (!currentEvent.get("customerId").contains(customerID)) {
							lockMontrealServerData.unlock();
							return false;
						}
						currentEvent.replace("customerId", customers.toString().replace(customerID.trim() + ",", ""));
						currentEvent.replace("capacity", currentEvent.get("capacity"),
								Integer.toString(Integer.parseInt(currentEvent.get("capacity"))));
						currentEvent.replace("totalBooking", currentEvent.get("totalBooking"),
								Integer.toString(Integer.parseInt(currentEvent.get("totalBooking")) - 1));
						typeData.replace(eventId, typeData.get(eventId), currentEvent);
						serverData.replace(eventType, serverData.get(eventType), typeData);
						lockMontrealServerData.unlock();
						return true;
					}
				} else {
					lockMontrealServerData.unlock();
					return false;
				}
			}
		} else {
			// System.out.println("No Event Type Found");
			lockMontrealServerData.unlock();
			return false;
		}
	}

	public String getBookingSchedule(String customerId) {
		lockMontrealServerData.lock();
		StringBuilder customers = new StringBuilder();
		for (Entry<String, HashMap<String, HashMap<String, String>>> types : serverData.entrySet()) {
			for (Entry<String, HashMap<String, String>> events : types.getValue().entrySet()) {
				if (events.getValue().get("customerId").contains(customerId.trim())) {
					customers.append(types.getKey() + " = " + events.getKey() + ",");
				}
			}
		}
		lockMontrealServerData.unlock();
		return customers.length() == 0 ? "" : customers.toString();
	}

	public String getBookingCount(String customerId, String eventType) {
		lockMontrealServerData.lock();
		int count = 0;
		String month = eventType.trim().substring(6, eventType.trim().length());
		for (Entry<String, HashMap<String, HashMap<String, String>>> types : serverData.entrySet()) {
			for (Entry<String, HashMap<String, String>> events : types.getValue().entrySet()) {
				if (events.getValue().get("customerId").trim().contains(customerId.trim())
						&& events.getKey().substring(6, events.getKey().trim().length()).trim().equals(month.trim())) {
					count++;
				}
			}
		}
		lockMontrealServerData.unlock();
		return Integer.toString(count);
	}

	public boolean getEvent(String customerId, String eventId, String eventType) {
		lockMontrealServerData.lock();
		if (serverData.containsKey(eventType)) {
			HashMap<String, HashMap<String, String>> typeData = serverData.get(eventType);
			if (typeData.size() == 0) {
				// System.out.println("No Events Found");
				lockMontrealServerData.unlock();
				return false;
			} else {
				if (typeData.containsKey(eventId)) {
					HashMap<String, String> currentEvent = typeData.get(eventId);
					lockMontrealServerData.unlock();
					return currentEvent.get("customerId").contains(customerId.trim());
				} else {
					lockMontrealServerData.unlock();
					return false;
				}
			}
		} else {
			// System.out.println("No Event Type Found");
			lockMontrealServerData.unlock();
			return false;
		}
	}
}