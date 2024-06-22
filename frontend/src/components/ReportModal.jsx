import React from "react";
import { Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from "@chakra-ui/react";
import { Button, Text, FormControl, FormLabel, Textarea } from "@chakra-ui/react";
import { useDisclosure } from "@chakra-ui/react";

const ReportModal = ({ buttonText, buttonSize, buttonRight, headerText, bodyText }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();

    let [value, setValue] = React.useState("")

    let handleInputChange = (e) => {
        let inputValue = e.target.value
        setValue(inputValue)
    }

    return (
        <>
            <Button size={buttonSize} right={buttonRight} onClick={onOpen}>{buttonText}</Button>

            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>{headerText}</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody pb={6}>
                        <FormControl>
                            <Text align="center" mb={6}>{bodyText}</Text>
                            <FormLabel>Details</FormLabel>
                            <Textarea
                                value={value}
                                onChange={handleInputChange}
                                placeholder="Enter additional details here"
                                size="sm"
                            />
                        </FormControl>
                </ModalBody>
                    <ModalFooter>
                        <Button colorScheme="blue" mr={6}>Submit</Button>
                        {/* todo <Button colorScheme="blue" mr={6} onClick={onClick}>Submit</Button> */}
                        <Button onClick={onClose}>Cancel</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default ReportModal;