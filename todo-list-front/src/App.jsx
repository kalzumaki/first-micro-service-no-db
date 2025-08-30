import './App.css';
import { useState, useEffect } from 'react';

const App = () => {
  const [tasks, setTasks] = useState([]);
  const [users, setUsers] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  // Fetch all tasks
  const fetchTasks = async () => {
    try {
      const response = await fetch('http://localhost:8080/tasks/all-task');
      if (!response.ok) throw new Error('Failed to fetch tasks');
      const tasksData = await response.json();
      setTasks(tasksData);
      return tasksData;
    } catch (error) {
      console.error('Error fetching tasks:', error);
      setError('Failed to load tasks');
      throw error;
    }
  };

  // Fetch user details
  const fetchUser = async (userId) => {
    try {
      const response = await fetch(`http://localhost:8000/users/${userId}`);
      if (!response.ok) throw new Error(`Failed to fetch user ${userId}`);
      const userData = await response.json();
      return userData;
    } catch (error) {
      console.error(`Error fetching user ${userId}:`, error);
      return { id: userId, name: 'Unknown User' };
    }
  };

  // Fetch all data when component mounts
  const fetchAllData = async () => {
    setLoading(true);
    setError('');
    
    try {
      const tasksData = await fetchTasks();
      
      // Fetch users for all tasks
      const userPromises = tasksData.map(task => fetchUser(task.userId));
      const usersData = await Promise.all(userPromises);
      
      // Create users map for easy access
      const usersMap = {};
      usersData.forEach(user => {
        usersMap[user.id] = user;
      });
      
      setUsers(usersMap);
    } catch (error) {
      setError('Failed to load data');
    } finally {
      setLoading(false);
    }
  };

  // Handle task submission
  const handleSubmitTask = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    
    const newTask = {
      description: formData.get('description'),
      userId: parseInt(formData.get('userId'))
    };

    try {
      const response = await fetch('http://localhost:8080/tasks/create-task', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newTask)
      });

      if (response.ok) {
        // Refresh tasks after successful creation
        await fetchAllData();
        event.target.reset(); // Clear form
      } else {
        throw new Error('Failed to create task');
      }
    } catch (error) {
      console.error('Error creating task:', error);
      setError('Failed to create task');
    }
  };

  // Fetch data on component mount
  useEffect(() => {
    fetchAllData();
  }, []);

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="content">
      <h1>Todo List</h1>
      
      {/* Tasks List */}
      <div className="tasks-container">
        <h2>Tasks</h2>
        {tasks.length === 0 ? (
          <p>No tasks found</p>
        ) : (
          tasks.map((task) => (
            <div key={task.id} className="task-card">
              <h3>{task.description}</h3>
              <p>
                <strong>Assigned to:</strong> {users[task.userId]?.name || 'Unknown User'} 
                (ID: {task.userId})
              </p>
              {task.title && <p><strong>Title:</strong> {task.title}</p>}
            </div>
          ))
        )}
      </div>

      {/* Add Task Form */}
      <div className="task-form">
        <h2>Add New Task</h2>
        <form onSubmit={handleSubmitTask}>
          <div className="form-group">
            <label htmlFor="description">Task Description:</label>
            <input 
              type="text" 
              id="description"
              name="description" 
              placeholder="Enter task description" 
              required 
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="userId">User ID:</label>
            <input 
              type="number" 
              id="userId"
              name="userId" 
              placeholder="Enter user ID" 
              min="1"
              required 
            />
          </div>
          
          <button type="submit">Add Task</button>
        </form>
      </div>

      {/* Refresh Button */}
      <button onClick={fetchAllData} className="refresh-btn">
        Refresh Data
      </button>
    </div>
  );
};

export default App;