import React from 'react';
import { Map, AdvancedMarker, Pin } from '@vis.gl/react-google-maps';
import ClusteredBikeRackMarkers from './ClusteredBikeRackMarkers';
import ClosestMarker from './InfoWindowMarker';

const mapId = import.meta.env.VITE_GOOGLE_MAPS_MAP_ID;

const BikeRackMap = ({ bikeRacks, center, setCenterNull, onMapBoundsChange, clickedMarkerCoordinates, onMapClick, closestMarker, scrollToBikeRackCard }) => {
    const handleCameraChanged = (ev) => {
        const bounds = ev.detail.bounds;
        const visibleBikeRacks = bikeRacks?.filter(({ poi }) => {
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
            defaultZoom={14}
            center={center}
            onCameraChanged={handleCameraChanged}
            onClick={onMapClick}
        >
            <ClusteredBikeRackMarkers
                bikeRacks={bikeRacks}
                scrollToBikeRackCard={scrollToBikeRackCard}    
            />

            {clickedMarkerCoordinates && (
                <AdvancedMarker
                    position={clickedMarkerCoordinates}
                >
                    <Pin background={'#FF0000'} glyphColor={'#000'} borderColor={'#000'} />
                </AdvancedMarker>
            )}

            {closestMarker && (
                <ClosestMarker
                    position={closestMarker.poi.location}
                    initiallyOpen={true}
                    infoWindowContent={closestMarker.address}
                    pinColour={'#00FF00'}
                />
            )}
        </Map>
    );  
}

export default BikeRackMap;