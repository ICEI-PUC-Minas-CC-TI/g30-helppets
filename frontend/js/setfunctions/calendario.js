const api = new ApiManager();

function createCalendarLayout(data = Date) {
    let toUse,
        daysInMonth;

    const month = data.getMonth(),
        year = data.getFullYear();

    if (data.getDate() != 0) {
        daysInMonth = new Date(year, month + 1, 0).getDate();
        toUse = new Date(year, month, 1);
    }
    else {
        toUse = data;
    }

    const calendar = document.querySelector("#calendar");
    
    calendar.innerHTML = "";

    Array.from(Array(daysInMonth).keys()).forEach(d => { // d + 1 = day
        const day = d + 1,
              dateByDay = new Date(year, month, day);

        const calendarItem = document.createElement("div");
        
        calendarItem.className = "calendar-item";

        if (day == 1) {
            for (let x = 0; x < dateByDay.getDay(); x++) {
                const grayCalendarItem = calendarItem.cloneNode();
                
                grayCalendarItem.className += " gray";

                calendar.appendChild(grayCalendarItem);

            }
            
        }
        const dataP = document.createElement("p");
            
        dataP.innerText = dateByDay.getDate();

        calendarItem.appendChild(dataP);
        
        calendarItem.addEventListener("click", () => {
            // TODO
        });

        calendar.appendChild(calendarItem);
    });

    while (calendar.childNodes.length < 42) {
        const calendarItem = document.createElement("div");
        
        calendarItem.className = "calendar-item gray";

        calendar.appendChild(calendarItem);
    }

    const calendarMonth = document.querySelector("#calendar-month");

    calendarMonth.innerText = toUse.toLocaleString("pt-br", {month: "long"});

    const prevMonthCalendarButton = document.querySelector("#prev-month-calendar"),
          nextMonthCalendarButton = document.querySelector("#next-month-calendar");

    prevMonthCalendarButton.addEventListener("click", function prevMonth(e) { 
        prevMonthCalendarButton.removeEventListener("click", prevMonth);
        createCalendarLayout(new Date(year, month - 1, 1));
    });

    nextMonthCalendarButton.addEventListener("click", function nextMonth(e) {
        nextMonthCalendarButton.removeEventListener("click", nextMonth);
        createCalendarLayout(new Date(year, month + 1, 1));
    });
}

(() => {
    Utilites.setupFooterContent();

    const currentData = new Date();

    createCalendarLayout(currentData);
})();