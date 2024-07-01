const submitNewRackReport = async ({reportType, details, userId, address, latitude, longitude, accessToken}) => {
    const requestBody = {
        reportType: reportType,
        details: details,
        userId: userId,
        address: address,
        latitude: latitude,
        longitude: longitude
    }; 

    const response = await fetch(
        `http://localhost:9000/report`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${accessToken}`
            },
            body: JSON.stringify(requestBody)
        }
    );

    const result = await response.json();

    if (!response.ok) {
        throw new Error(`Error submitting new rack report.`);
    }

    return result;
}
export default submitNewRackReport;