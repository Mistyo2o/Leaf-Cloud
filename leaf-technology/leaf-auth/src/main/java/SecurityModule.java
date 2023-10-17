import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouHui
 * @description 安全鉴权模块组件自动扫描机制
 */

@Configuration
@ComponentScan(basePackageClasses = SecurityModule.class)
public class SecurityModule {
}
