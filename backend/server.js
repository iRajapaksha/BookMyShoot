const dotenv = require('dotenv')
const express = require('express')
const mongoose = require('mongoose')
dotenv.config();
const app = require('./src/app')

const PORT = process.env.PORT || 3005;

// MongoDB URI
const dbURI = process.env.MONGODB_URI || 'mongodb+srv://nayanajithishara2541:jZuY0drG4i25ukXo@cluster0.wf6p9so.mongodb.net/yourDatabaseName?retryWrites=true&w=majority';

// Connect to MongoDB
mongoose.connect(dbURI, {})
  .then(() => {
    console.log('Connected to MongoDB');
    // Start your application logic here
    app.listen(PORT, () => {
      console.log(`Example app listening on port ${PORT}`);
    });
  })
  .catch(err => {
    console.error('MongoDB connection error:', err);
  });