# kafkaCQRSDemo
Faced 2 most time-consuming challenges - always remember the folder structure naming for Producer and consumer should be same as while Producer publish message to topic, Producer Entity is passed in header along with folder path.
If folder name along with the path of both producer and consumer wont be same then while consuming the message by consumer, the header producer Entity along with path will also be consumed and it will try to fetch the entity from the same path. So consumer must also have the same path & name for Entity like producer to match the same folder structure. 
Again, If Producer entity Id which is primary key is auto incremented, in consumer the same entity should not be auto-generated as it will creae a conflict of optimisticLockoperation, so to avoid that consumer should fed the same data produced by producer.
