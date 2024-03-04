import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Box, Typography, Alert, AlertTitle } from '@mui/material';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import defaultVariables from '../variables/variables';

const AddPeopleForm = () => {

    const [personName, setPersonName] = useState('');
    const [personEmail, setPersonEmail] = useState('');
    const [personPassword, setPersonPassword] = useState('');
    const [personAadhaar, setPersonAadhaar] = useState('');
    const [personPhone, setPersonPhone] = useState('');

    const [successMessage, setSuccessMessage] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    const handleSubmit = (e) => {
    e.preventDefault();

    const community = {
      person_name: personName,
      person_email: personEmail,
      person_password: personPassword,
      person_aadhaar: personAadhaar,
      person_phone: personPhone
    };

    axios.post(defaultVariables['backend-url'] + "people/add", community).then((res) => {
      setSuccessMessage("People added successfully");
      setTimeout(() => {
        setSuccessMessage(null);
      },3000)
      
    }).catch(err => {
      setErrorMessage("Some error occured");
      setTimeout(() => {
        setErrorMessage(null);
      },3000)
    })
    // Handle form submission logic here
  };

  return (
    <div>
    <div className='form-div'>
      <br />
      <p className="heading-medium" style={{ textAlign: "left" }}>Add People</p>
      <br />
      <form onSubmit={handleSubmit} style={{ display: 'grid', gridTemplateColumns: '50% 50%', gap: '10px' }}>
        <TextField
          label="Name"
          variant="outlined"
          value={personName}
          onChange={(e) => setPersonName(e.target.value)}
          fullWidth
          margin="normal"
          required
        />

        <TextField
          label="Email"
          variant="outlined"
          value={personEmail}
          onChange={(e) => setPersonEmail(e.target.value)}
          fullWidth
          margin="normal"
          required
        />

        <TextField
          label="Password"
          variant="outlined"
          value={personPassword}
          onChange={(e) => setPersonPassword(e.target.value)}
          fullWidth
          margin="normal"
          required
        />

        <TextField
          label="Aadhaar"
          variant="outlined"
          value={personAadhaar}
          onChange={(e) => setPersonAadhaar(e.target.value)}
          fullWidth
          margin="normal"
          required
        />

        <TextField
          label="Phone"
          variant="outlined"
          value={personPhone}
          onChange={(e) => setPersonPhone(e.target.value)}
          fullWidth
          margin="normal"
          required
        />
    
        <Button type="submit" variant="contained" color="primary" fullWidth id="react-button">
          Submit
        </Button>
      </form>
    </div>
    {successMessage && (
        <div style={{ position: 'fixed', top: '60px', right: '20px', zIndex: 9999 }}>
          <Alert severity="success" onClose={() => setSuccessMessage(null)} variant="standard">
            <AlertTitle>Success</AlertTitle>
            <strong>{successMessage}</strong>
          </Alert>
        </div>
      )}
      {errorMessage && (
        <div style={{ position: 'fixed', top: '60px', right: '20px', zIndex: 9999 }}>
          <Alert severity="error" onClose={() => setErrorMessage(null)} variant = "standard" width = {{sx : 300}}>
            <AlertTitle>Error</AlertTitle>
            <strong>{errorMessage}</strong>
          </Alert>
        </div>
      )}
    </div>
  );
};

export default AddPeopleForm;