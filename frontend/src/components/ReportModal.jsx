import React from "react";
import { Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from "@chakra-ui/react";
import { Button, Text, FormControl, FormLabel, Textarea, useDisclosure, useToast } from "@chakra-ui/react";
import { useKeycloak } from "@react-keycloak/web";

const ReportModal = ({ rackId, reportType, address, buttonSize, buttonRight }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const {keycloak} = useKeycloak();
    const toast = useToast();

    let [value, setValue] = React.useState("")

    let handleInputChange = (ev) => {
        let inputValue = ev.target.value
        setValue(inputValue)
    }

    const handleOpen = () => {
        if (keycloak.authenticated) {
            onOpen();
        } else {
            toast({
                title: "Login to Access",
                description: "Please login to submit a report.",
                status: "info",
                duration: 5000,
                isClosable: true,
            })
        }
    }

    const onClickSubmit = (rackId, reportType) => {
        console.log("submit: " + rackId, +" "+reportType);
    }

    return (
        <>
            <Button size={buttonSize} right={buttonRight} onClick={handleOpen}>
                {`Report ${reportType}`}
            </Button>

            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>
                        {`Report ${reportType}`}
                    </ModalHeader>
                    <ModalCloseButton />
                    <ModalBody pb={6}>
                        <FormControl>
                            <Text align="center" mb={6}>
                                {`Are you sure you want to report ${reportType.toLowerCase()} at ${address}?`}
                            </Text>
                            <FormLabel>Details</FormLabel>
                            <Textarea
                                value={value}
                                onChange={handleInputChange}
                                placeholder="Enter additional details here"
                                size="sm"
                                maxLength={255}
                            />
                        </FormControl>
                </ModalBody>
                    <ModalFooter>
                        <Button colorScheme="blue" mr={6} onClick={() => onClickSubmit(rackId, reportType)}>Submit</Button>
                        <Button onClick={onClose}>Cancel</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default ReportModal;