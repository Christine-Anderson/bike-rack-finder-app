const submitReport = async ({rackId, reportType, details, userId, accessToken}) => {
    const requestBody = {
        rackId: rackId,
        reportType: reportType,
        details: details,
        userId: userId
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
        throw new Error(`Error submitting ${reportType} report for bike rack ${rackId}`);
    }

    return result;
}
export default submitReport;