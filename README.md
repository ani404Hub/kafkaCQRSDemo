# kafkaCQRSDemo
Faced 2 most time consuming challanges - always remember the folder structer for Producer and consumer should be same as while Producer publish message to topic, Producer Entity is passed in header along with folder structure.
If folder structure of both producer and consumer wont be same then while consuming the message by consumer, the header Producer Entity will be consumed and it will try to fetch the entity from same path. So consumer must also have the same path for Entity like producer to match the same folder structure. 
