import { Map, AdvancedMarker, Pin } from '@vis.gl/react-google-maps';
import ClusteredBikeRackMarkers from './ClusteredBikeRackMarkers';

const mapId = import.meta.env.VITE_GOOGLE_MAPS_MAP_ID;

const BikeRackMap = ({ bikeRacks, center, setCenterNull, onMapBoundsChange, clickedMarkerCoordinates, onMapClick }) => {
    const handleCameraChanged = (ev) => {
        console.log('camera changed:', ev.detail.center, 'zoom:', ev.detail.zoom);
        const bounds = ev.detail.bounds;
        const visibleBikeRacks = bikeRacks.filter(({ poi }) => {
            const lat = poi.location.lat;
            const lng = poi.location.lng;
            return lat >= bounds.south && lat <= bounds.north && lng >= bounds.west && lng <= bounds.east;
        });
        onMapBoundsChange(visibleBikeRacks);
        setCenterNull();
    };

    return (
        <Map
            mapId={mapId}
            defaultZoom={13}
            center={center}
            onCameraChanged={handleCameraChanged}
            onClick={onMapClick}
        >
            <ClusteredBikeRackMarkers bikeRacks={bikeRacks} />

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