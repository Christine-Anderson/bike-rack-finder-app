import React from 'react';
import { Input, IconButton, Flex } from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";

const SearchBar = ({ searchValue, setSearchValue, handleSearch }) => {
    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSearch();
        }
    };

    return (
        <Flex alignItems="center" justifyContent="center" m={3}>
            <Input
                placeholder="Search..."
                variant="filled"
                size="md"
                mr={3}
                value={searchValue}
                onChange={(e) => setSearchValue(e.target.value)}
                onKeyPress={handleKeyPress}
            />
            <IconButton
                colorScheme="blue"
                aria-label="Search database"
                icon={<SearchIcon />}
                onClick={handleSearch}
            />
        </Flex>
    );
};

export default SearchBar;
