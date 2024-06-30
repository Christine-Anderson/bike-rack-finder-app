const submitBikeRackRating = async ({rackId, newRating, accessToken}) => {
    const response = await fetch(
        `http://localhost:9000/bikeRack/${rackId}/rating?newRating=${newRating}`,
        {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${accessToken}`
            },
        }
    );

    const result = await response.json();

    if (!response.ok) {
        throw new Error(`Error updating rating for bike rack ${rackId}`);
    }

    return result;
}
export default submitBikeRackRating;