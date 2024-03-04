import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Box, Typography, Alert, AlertTitle } from '@mui/material';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import defaultVariables from '../variables/variables';

const AddGrievanceForm = () => {

    const [grievanceTitle, setGrievanceTitle] = useState('');
    const [grievanceDescription, setGrievanceDescription] = useState('');
    const [grievancePerson, setGrievancePerson] = useState('');

    const [successMessage, setSuccessMessage] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    const handleSubmit = (e) => {
        e.preventDefault();

        const grievance = {
            grievance_title: grievanceTitle,
            grievance_description: grievanceDescription,
            grievance_person: localStorage.getItem("userid")
        };

        axios.post(defaultVariables['backend-url'] + "grievance/add", grievance).then((res) => {
            setSuccessMessage(res.data);
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
      <p className="heading-medium" style={{ textAlign: "left" }}>Add Grievance</p>
      <br />
      <form onSubmit={handleSubmit} >
        <TextField
          label="Title"
          variant="outlined"
          value={ grievanceTitle }
          onChange={(e) => setGrievanceTitle(e.target.value)}
          fullWidth
          margin="normal"
          required
        />

        <TextField
          label="Description"
          variant="outlined"
          value={ grievanceDescription }
          onChange={(e) => setGrievanceDescription(e.target.value)}
          fullWidth
          multiline
					rows={4}
          margin="normal"
          required
        />
    
        <br /><br />
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

export default AddGrievanceForm;