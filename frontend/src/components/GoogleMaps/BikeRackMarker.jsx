import React, {useCallback} from 'react';
import {AdvancedMarker, Pin} from '@vis.gl/react-google-maps';

const BikeRackMarker = ({ rack, onClick, setMarkerRef, scrollToCard }) => {

    const handleClick = useCallback(() => {
        onClick(rack);
        scrollToCard(rack.poi.rackId);
    }, [onClick, rack, scrollToCard]);

    const ref = useCallback(
        (marker) => setMarkerRef(marker, rack.poi.rackId),
        [setMarkerRef, rack.key]
    );

    return (
        <AdvancedMarker 
            position={rack.poi.location}
            ref={ref}
            onClick={handleClick}>
            <Pin background={'#FBBC04'} glyphColor={'#000'} borderColor={'#000'} />
        </AdvancedMarker>
    );
};

export default BikeRackMarker;
