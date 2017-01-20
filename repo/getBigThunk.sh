
rm -rf com
mkdir -p com/bigthunk

pushd .

cd ../../BigThunkCore
git pull
./gitStuff.sh
mvn clean install

cd ../BigThunkWeb
git pull
./gitStuff.sh
mvn clean install

popd

cp -vR ~/.m2/repository/com/bigthunk/* com/bigthunk
