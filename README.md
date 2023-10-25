# Meeting-planner-API-v1


## Description

This Meeting Planner streamlines the management of company meeting rooms, with a specific focus on addressing the challenges posed by COVID-19.

1. **Meeting Request:** Capture and record meeting requests.

2. **Availability Check:** Identify available meeting rooms at the desired time, ensuring that each room remains unreserved for one hour before and after the meeting to accommodate cleaning requirements.

3. **Capacity Verification:** Confirm whether the available meeting rooms can accommodate the required number of participants based on their individual room capacities.

4. **Equipment Compatibility:** Select meeting rooms equipped with the necessary tools and resources tailored to the specific type of meeting.

5. **Booking:** Finalize the booking of the selected meeting room.

## Prerequisites
  **Java 17 or higher**

## Getting Started

To get started with the Meeting Room Planner, follow these steps:

1. **Clone this repository** to your local machine using the following command:

   ```bash
   git clone repolink
2. **Create MySQL Database:** You need to create a MySQL database with the name meeting_planner_db. Use the following command in your MySQL client:
    ```bash
    CREATE DATABASE meeting_planner_db;
3. **Run the Application:**
After cloning the repository and setting up the database, you can run the application.Once the API is running, you can test it by accessing the following URL:
  http://localhost:8085/api/v1/meeting/meeting-request

For detailed API documentation, you can refer to the Swagger UI at:
http://localhost:8085/swagger-ui/index.html
