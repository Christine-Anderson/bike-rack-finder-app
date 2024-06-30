const fetchBikeRacks = async () => {    
    const response = await fetch(
        "http://localhost:9000/bikeRack"
    );
    
    const result = await response.json();

    if (!response.ok) {
        throw new Error("Error fetching bike racks");
    }
    
    return result;
}
export default fetchBikeRacks;