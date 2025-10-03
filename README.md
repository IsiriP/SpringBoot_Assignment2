# SpringBoot_Assignment2
# TaskHub – Developer Task Tracker

TaskHub is a Spring Boot application designed to help teams efficiently manage projects, developers, and their assignments.  
It provides a clear overview of ongoing tasks, deadlines, and developer workload, making it ideal for startups and small teams who want to improve productivity and accountability.

Key Features

- Organize and track projects with ease  
- Add and manage developers, and assign them to multiple projects  
- Assign, monitor, and prioritize tasks for each developer  
- Update task progress with statuses like TODO, IN_PROGRESS, DONE  
- Monitor task deadlines and highlight overdue work  
- Automatically restrict developers from being overloaded with too many tasks  
- Lock completed tasks to prevent accidental changes  
- Identify top performers or developers struggling with overdue tasks  
- View all tasks of a developer or project in a single glance  

API Overview

Projects

->Create Project 
POST /api/projects  
Example:
{
  "name": "Project Phoenix",
  "description": "Revamp of the legacy system"
}

->Get All Projects
GET /api/projects

->Get Project Details
GET /api/projects/{projectId}

->Developers
Add Developer to a Project
POST /api/developers/{projectId}
Example:
{
  "name": "Isiri",
  "email": "isiri@gmail.com"
}

Assign Developer to Additional Project
PUT /api/developers/{developerId}/assign/{projectId}

Fetch Developer Details
GET /api/developers/{developerId}

List Developers of a Project
GET /api/developers/project/{projectId}

Tasks

Create Task for a Developer
POST /api/tasks/developer/{developerId}
Example:

{
  "title": "Setup CI/CD pipeline",
  "description": "Automate build and deployment",
  "status": "TODO",
  "priority": "MEDIUM",
  "dueDate": "2025-10-07"
}


View All Tasks for a Developer
GET /api/tasks/developer/{developerId}

Update Task Status
PUT /api/tasks/{taskId}/status?status=IN_PROGRESS

Get Overdue Tasks Across Projects
GET /api/tasks/overdue

Top 3 Developers with Most Pending Tasks
GET /api/tasks/top-overdue

->Business Rules

A developer cannot have more than 5 active tasks at a time
Tasks marked as DONE are immutable
Overdue tasks are those past their due date and not yet completed

->Database
Postgres database
database name: taskhub

Quick Test Flow
1.Create a project

2.Add a developer to it

3.Assign tasks to the developer

4.Update task status and check overdue tasks

5.Try adding more than 5 IN_PROGRESS tasks (should fail)

6.Mark a task as DONE and try updating it (should fail)

7.Check /tasks/top-overdue to see the top 3 developers with late tasks
