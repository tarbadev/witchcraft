const backendUrl = process.env.BACKEND_URL ? process.env.BACKEND_URL : 'http://localhost:8080'
global.actuatorUrl = `${backendUrl}/actuator`
global.appUrl = process.env.APP_URL ? process.env.APP_URL : 'http://localhost:5000'