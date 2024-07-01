import React, { useState, useCallback, useEffect } from 'react';
import { AdvancedMarker, InfoWindow, useAdvancedMarkerRef, Pin } from '@vis.gl/react-google-maps';

const InfoWindowMarker = ({ position, initiallyOpen = false, infoWindowContent, pinColour}) => {
    const [markerRef, marker] = useAdvancedMarkerRef();
    const [infoWindowShown, setInfoWindowShown] = useState(initiallyOpen);

    const handleMarkerClick = useCallback(
        () => setInfoWindowShown(isShown => !isShown),
        []
    );

    const handleClose = useCallback(() => setInfoWindowShown(false), []);

    useEffect(() => {
        setInfoWindowShown(initiallyOpen);
    }, [initiallyOpen]);

    return (
        <>
            <AdvancedMarker
                ref={markerRef}
                position={position}
                onClick={handleMarkerClick}
            >
                <Pin background={pinColour} glyphColor={'#000'} borderColor={'#000'} />
            </AdvancedMarker>

            {infoWindowShown && (
                <InfoWindow anchor={marker} onClose={handleClose}>
                    <h1>{infoWindowContent}</h1>
                </InfoWindow>
            )}
        </>
    );
};

export default InfoWindowMarker;