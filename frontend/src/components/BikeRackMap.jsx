import { Map, AdvancedMarker, Pin } from '@vis.gl/react-google-maps';

const mapId = import.meta.env.VITE_GOOGLE_MAPS_MAP_ID;

const defaultCoordinates = {
    lat: 49.2827,
    lng: -123.1207
};

const BikeRackMap = ({mockBikeRacks}) => {
    const mockBikeRackCoord = mockBikeRacks.map(({ poi }) => ({
        key: poi.key,
        location: poi.location
    }));

    return (
        <Map
            mapId={mapId}
            defaultZoom={13}
            defaultCenter={ { lat: defaultCoordinates.lat, lng: defaultCoordinates.lng } }
            onCameraChanged={ (ev) =>
                console.log('camera changed:', ev.detail.center, 'zoom:', ev.detail.zoom)
            }>
            {mockBikeRackCoord.map( (mockBikeRackCoord) => (
                <AdvancedMarker
                    key={mockBikeRackCoord.key}
                    position={mockBikeRackCoord.location}>
                <Pin background={'#FBBC04'} glyphColor={'#000'} borderColor={'#000'} />
                </AdvancedMarker>
            ))}
        </Map>
    );  
}

export default BikeRackMap;