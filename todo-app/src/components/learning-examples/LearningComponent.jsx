import SecondComponent from "./SecondComponent"
import FirstComponent from "./FirstComponent"
import ThirdComponent from "./ThirdComponent"
import FourthComponent from "./FourthComponent"
import { FifthComponent } from "./FirstComponent"
import LearningJavaScript from "./LearningJavaScript"

export default function LearningComponent() {
    return(
       <div className="LearningComponent">
        <FirstComponent />
        <SecondComponent />
        <ThirdComponent></ThirdComponent>
        <FourthComponent></FourthComponent>
        <FifthComponent />
        <LearningJavaScript />
      </div>
    )
  }