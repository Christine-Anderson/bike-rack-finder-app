import { Map, AdvancedMarker, Pin } from '@vis.gl/react-google-maps';

const mapId = import.meta.env.VITE_GOOGLE_MAPS_MAP_ID;

const defaultCoordinates = {
    lat: 49.2827,
    lng: -123.1207
};

const BikeRackMap = ({ mockBikeRacks, onMapBoundsChange, clickedMarkerCoordinates, onMapClick }) => {
    const handleCameraChanged = (ev) => {
        console.log('camera changed:', ev.detail.center, 'zoom:', ev.detail.zoom);
        const bounds = ev.detail.bounds;
        const visibleBikeRacks = mockBikeRacks.filter(({ poi }) => {
            const lat = poi.location.lat;
            const lng = poi.location.lng;
            return lat >= bounds.south && lat <= bounds.north && lng >= bounds.west && lng <= bounds.east;
        });
        onMapBoundsChange(visibleBikeRacks);
    };

    return (
        <Map
            mapId={mapId}
            defaultZoom={13}
            defaultCenter={ { lat: defaultCoordinates.lat, lng: defaultCoordinates.lng } }
            onCameraChanged={handleCameraChanged}
            onClick={onMapClick}
        >
            {mockBikeRacks.map(({ poi }) => (
                <AdvancedMarker
                    key={poi.key}
                    position={poi.location}>
                <Pin background={'#FBBC04'} glyphColor={'#000'} borderColor={'#000'} />
                </AdvancedMarker>
            ))}

            {clickedMarkerCoordinates && (
                <AdvancedMarker
                    position={clickedMarkerCoordinates}
                >
                    <Pin background={'#FF0000'} glyphColor={'#000'} borderColor={'#000'} />
                </AdvancedMarker>
            )}
        </Map>
    );  
}

export default BikeRackMap;