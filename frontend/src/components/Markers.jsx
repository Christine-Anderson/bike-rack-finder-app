import { AdvancedMarker, Pin } from "@vis.gl/react-google-maps";

const Markers = ({mockBikeRackCoord}) => {
    return (
        <>
            {mockBikeRackCoord.map( (mockBikeRackCoord) => (
                <AdvancedMarker
                    key={mockBikeRackCoord.key}
                    position={mockBikeRackCoord.location}>
                <Pin background={'#FBBC04'} glyphColor={'#000'} borderColor={'#000'} />
                </AdvancedMarker>
            ))}
        </>
    );
}

export default Markers;