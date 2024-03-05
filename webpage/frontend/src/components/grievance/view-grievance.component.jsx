import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from "react-router-dom";
import defaultVariables from '../variables/variables';

import './view-grievance.css';

const ViewGrievance = () => {

    let { id } = useParams();

    const [grievance, setGrievance] = useState([]);
    const [totalSessions, setTotalSessions] = useState(0);
    const [totalBeneficiaries, setTotalBeneficiaries] = useState(0);
    const [totalActivities, setTotalActivities] = useState(0);
    const [totalParticipations, setTotalParticipations] = useState(0);

    const [loadGetGender, setLoadGetGender] = useState(false);
    const [loadGetEmployed, setLoadGetEmployed] = useState(false);
    const [loadGetAadhaarPan, setLoadGetAadhaarPan] = useState(false);
    const [loadGetBankAccount, setLoadGetBankAccount] = useState(false);
    const [loadGetParticipation, setLoadGetParticipation] = useState(false);
    const [loadGetAAC, setLoadGetAAC] = useState(false);

    const [getGender, setGetGender] = useState({});
    const [getEmployed, setGetEmployed] = useState({});
    const [getAadhaarPan, setGetAadhaarPan] = useState({});
    const [getBankAccount, setGetBankAccount] = useState({});
    const [getParticipation, setGetParticipation] = useState({});
    const [getAAC, setGetAAC] = useState({});

    useEffect(() => {
        axios.get(defaultVariables['backend-url'] + "grievance/view/get?id=" + id)
            .then((res) => {
                setGrievance(res.data[0]);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        // <div className='activity-details'>
        //     <p className='heading-medium' >{community.name}</p>
        //     <div className='all-details-div'>
        //         <div className='details-div'>
        //             <p className='details-field'>Name: </p>
        //             <p className='details-value'>{community.name}</p>
        //         </div>
        //         <div className='details-div'>
        //             <p className='details-field'>Challenges: </p>
        //             <p className='details-value'>{community.challenges}</p>
        //         </div>
        //         <div className='details-div'>
        //             <p className='details-field'>Population: </p>
        //             <p className='details-value'>{community.totalPopulation}</p>
        //         </div>
        //     </div>
        // </div>
        <div className='activity-details'>
            <p className='heading-medium' >{ grievance.grievanceTitle }</p>
            <div className='all-details-div'>
                <div className='details-div'>
                    <p className='details-field'>Title: </p>
                    <p className='details-value'>{ grievance.grievanceTitle }</p>
                </div>
                <div className='details-div'>
                    <p className='details-field'>Description: </p>
                    <p className='details-value'>{ grievance.grievanceDescription }</p>
                </div>
                <div className='details-div'>
                    <p className='details-field'>Department: </p>
                    <p className='details-value'>{ grievance.grievanceDepartment }</p>
                </div>
            </div>
        </div>
    )
};

export default ViewGrievance;