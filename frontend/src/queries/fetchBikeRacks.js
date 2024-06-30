const fetchBikeRacks = async () => {    
    const res = await fetch(
        "http://localhost:9000/bikeRack"
    );
    
    if (!res.ok) {
        throw new Error("Error fetching bike racks");
    }
    
    return res.json();
}
export default fetchBikeRacks;