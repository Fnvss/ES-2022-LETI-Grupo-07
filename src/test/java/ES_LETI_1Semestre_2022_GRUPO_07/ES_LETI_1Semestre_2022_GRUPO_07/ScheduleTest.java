package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScheduleTest {

	Schedule schedule;
	List<Event> events = new ArrayList<>();
	Element element1 = new Element("Rafael", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ramss3@iscte.pt&password=gGdsqZehHOOyFFAPMPSk8VeB63LPV0scP4qR36srE3dMgEB3b8GPIxCKorPUzQeySmm0XoAR7K0gw8pA5QrVrvx1VwfVrgjOFhMlC0mQkG6UxWuE4R2n12xxgV9hYfbX");
	Element element2 =  new Element("Filipe", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=fnvss@iscte.pt&password=QXPQEvIYJf6oR7oX3P7Ua19IGGIJTUD2Zmd6q7aqOaxmefDktt4TD0rSiMefsxnRwD8C9w4FmwWsKqF8wIPpxrTVisf31hZMd8KUAUERCtDWOfeWnM64j7k1jV8jbVE6");
	List<Element> elements = new ArrayList<>();
	Event event1 = new Event(LocalDateTime.of(2023, 1, 4, 16, 30), LocalDateTime.of(2022, 1, 4, 19, 0), "Arquitetura de Redes", elements);;
	Event event2 = new Event(LocalDateTime.of(2022, 12, 15, 11, 0),LocalDateTime.of(2022, 12, 15, 12, 30), "Engenharia de Software", element1);
	Event event3 = new Event(LocalDateTime.of(2022, 12, 15, 8, 0),LocalDateTime.of(2022, 12, 15, 9, 30), "Engenharia de Software", elements);
	Event event4 = new Event(LocalDateTime.of(2022, 12, 14, 8, 0),LocalDateTime.of(2022, 12, 14, 8, 30), "Reunião", elements);
	TimeOfDay timeOfDay;
	int duration;
	int periodicity;
	
	@BeforeEach
	void setUp() throws Exception {
		elements.add(element1);
		elements.add(element2);
		events.add(event1);
		events.add(event2);
		events.add(event3);
		schedule = new Schedule(events, elements);
		
	}
	
	@Test
	void testGetUpperChars() {
		assertEquals("AR", schedule.getUpperChars(event1.getSummary(), ""));
	}

	@Test
	void testReadCalendar() {
		List<Event> filteredList = events.stream().filter(event -> event.getElements().contains(element1)).collect(Collectors.toList());
		List<Event> newList = new ArrayList<>();
		for(Event e : filteredList) {
			newList.add(e);
		}
		
		assertEquals(new Schedule(events, element1), schedule.readCalendar(element1));
	}

	@Test
	void testPeriodicReunion() throws FileNotFoundException {
		List<Event> eventsList = new ArrayList<>();
		Event eventTeste = new Event(LocalDateTime.of(2022, 12, 14, 8, 0), LocalDateTime.of(2022, 12, 14, 8, 30), "Reunião", elements);
		Event eventTeste2 = new Event(LocalDateTime.of(2022, 12, 19, 8, 0), LocalDateTime.of(2022, 12, 19, 8, 30), "Reunião", elements);
		Event eventTeste3 = new Event(LocalDateTime.of(2022, 12, 26, 8, 0), LocalDateTime.of(2022, 12, 26, 8, 30), "Reunião", elements);
		eventsList.add(eventTeste);
		eventsList.add(eventTeste2);
		eventsList.add(eventTeste3);
		assertEquals(eventsList, schedule.periodicReunion(elements, TimeOfDay.MANHA, 30, 1, 3));
	}

	@Test
	void testFilteredEvents() {
		List<Event> eventsList = new ArrayList<>();
		eventsList.add(event3);
		eventsList.add(event2);
		assertEquals(eventsList, schedule.filteredEvents(TimeOfDay.MANHA, elements));
	}

	@Test
	void testCheckAvailableDate() throws FileNotFoundException {	
		assertEquals(event4, schedule.checkAvailableDate(elements, TimeOfDay.MANHA, 30));
	}

	@Test
	void testGetElements() {
		assertEquals(elements, schedule.getElements());
	}

	@Test
	void testGetEvents() {
		assertEquals(events, schedule.getEvents());
	}

}
