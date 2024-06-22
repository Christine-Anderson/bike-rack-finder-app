import React from "react";
import { Box, Icon } from "@chakra-ui/react";
import { BsStarFill, BsStarHalf, BsStar } from "react-icons/bs";

const StarRating = ({ rating }) => {
    const stars = [];
    for (let index = 0; index < 5; index++) {
        const filled = index < Math.floor(rating);
        const half = !filled && index < Math.ceil(rating);
        stars.push(
            <Icon
                key={index}
                as={filled ? BsStarFill : half ? BsStarHalf : BsStar}
                color={"yellow.300"}
                boxSize={6}
            />
        );
    }

    return <Box>{stars}</Box>;
};

export default StarRating;