import { Map } from '@vis.gl/react-google-maps';

const mapId = import.meta.env.VITE_GOOGLE_MAPS_MAP_ID;

const defaultCoordinates = {
    lat: 49.2827,
    lng: -123.1207
};

const BikeRackMap = () => {
    return (
        <Map
            mapId={mapId}
            defaultZoom={13}
            defaultCenter={ { lat: defaultCoordinates.lat, lng: defaultCoordinates.lng } }
            onCameraChanged={ (ev) =>
                console.log('camera changed:', ev.detail.center, 'zoom:', ev.detail.zoom)
            }>
        </Map>
    );  
}

export default BikeRackMap;